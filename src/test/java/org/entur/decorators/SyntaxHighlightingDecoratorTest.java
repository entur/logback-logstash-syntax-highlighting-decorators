package org.entur.decorators;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringWriter;

import org.entur.jackson.tools.jsh.SyntaxHighlighter;
import org.entur.jackson.tools.jsh.SyntaxHighlightingPrettyPrinter;
import org.junit.jupiter.api.Test;

import org.entur.decorators.factory.SyntaxHighlighterFactory;
import org.entur.decorators.syntaxhighlight.ListSyntaxHighlighter;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.json.JsonFactory;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

public class SyntaxHighlightingDecoratorTest {

	@Test
	public void testSingle() throws IOException {
		SyntaxHighlightingMapperBuilderDecorator mapperDecorator = new SyntaxHighlightingMapperBuilderDecorator();
		SyntaxHighlightingJsonGeneratorDecorator jsonGeneratorDecorator = new SyntaxHighlightingJsonGeneratorDecorator();

		final SubtreeJsonStreamContextListener listener = new SubtreeJsonStreamContextListener();
		mapperDecorator.addSyntaxHighlighterFactory(new SyntaxHighlighterFactory() {

			@Override
			public SyntaxHighlighter createSyntaxHighlighter() {
				return listener;
			}

		});

		SyntaxHighlighter syntaxHighlighter = mapperDecorator.createSyntaxHighlighter();
		assertFalse(syntaxHighlighter instanceof ListSyntaxHighlighter);

		StringWriter writer = new StringWriter();

		ObjectMapper mapper = mapperDecorator.decorate(JsonMapper.builderWithJackson2Defaults()).build();

		JsonGenerator partiallyDecorated = mapper.createGenerator(writer);

		// check that the json stream context part is wired correctly
		JsonGenerator decorated = jsonGeneratorDecorator.decorate(partiallyDecorated);
		decorated.writeStartObject();
		assertEquals(1, listener.getLevel());
		decorated.writeEndObject();
		assertEquals(0, listener.getLevel());
	}

	@Test
	public void testMultiple() throws IOException {
		SyntaxHighlightingMapperBuilderDecorator mapperDecorator = new SyntaxHighlightingMapperBuilderDecorator();
		SyntaxHighlightingJsonGeneratorDecorator jsonGeneratorDecorator = new SyntaxHighlightingJsonGeneratorDecorator();

		final SubtreeJsonStreamContextListener first = new SubtreeJsonStreamContextListener();
		mapperDecorator.addSyntaxHighlighterFactory(new SyntaxHighlighterFactory() {

			@Override
			public SyntaxHighlighter createSyntaxHighlighter() {
				return first;
			}

		});
		final SubtreeJsonStreamContextListener second = new SubtreeJsonStreamContextListener();
		mapperDecorator.addSyntaxHighlighterFactory(new SyntaxHighlighterFactory() {

			@Override
			public SyntaxHighlighter createSyntaxHighlighter() {
				return second;
			}

		});
		SyntaxHighlighter syntaxHighlighter = mapperDecorator.createSyntaxHighlighter();
		assertTrue(syntaxHighlighter instanceof ListSyntaxHighlighter);

		ObjectMapper mapper = mapperDecorator.decorate(JsonMapper.builderWithJackson2Defaults()).build();

		StringWriter writer = new StringWriter();
		JsonGenerator partiallyDecorated = mapper.createGenerator(writer);

		// check that the json stream context part is wired correctly
		JsonGenerator decorated = jsonGeneratorDecorator.decorate(partiallyDecorated);
		decorated.writeStartObject();
		assertEquals(1, first.getLevel());
		assertEquals(1, second.getLevel());
		decorated.writeEndObject();
		assertEquals(0, first.getLevel());
		assertEquals(0, second.getLevel());
	}
}
