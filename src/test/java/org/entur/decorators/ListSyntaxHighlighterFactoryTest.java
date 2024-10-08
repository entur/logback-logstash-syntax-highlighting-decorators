package org.entur.decorators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerator;
import org.entur.decorators.factory.ListSyntaxHighlighterFactory;
import org.entur.decorators.factory.SyntaxHighlighterFactory;
import org.entur.decorators.syntaxhighlight.ListSyntaxHighlighter;
import org.entur.jackson.jsh.AnsiSyntaxHighlight;
import org.entur.jackson.jsh.DefaultSyntaxHighlighter;
import org.entur.jackson.jsh.SyntaxHighlighter;

public class ListSyntaxHighlighterFactoryTest {

	@Test
	public void testSingle() {
		ListSyntaxHighlighterFactory factory = new ListSyntaxHighlighterFactory();

		factory.addSyntaxHighlighterFactory(new SyntaxHighlighterFactory() {

			@Override
			public SyntaxHighlighter createSyntaxHighlighter(JsonGenerator generator) {
				return DefaultSyntaxHighlighter.newBuilder().withComma(AnsiSyntaxHighlight.GREEN).build();
			}

		});

		SyntaxHighlighter syntaxHighlighter = factory.createSyntaxHighlighter(null);
		assertFalse(syntaxHighlighter instanceof ListSyntaxHighlighter);
		assertEquals(AnsiSyntaxHighlight.build(AnsiSyntaxHighlight.GREEN), syntaxHighlighter.forComma());
	}

	@Test
	public void testMultiple() {
		SyntaxHighlightingDecorator factory = new SyntaxHighlightingDecorator();

		String[] colors = new String[] { AnsiSyntaxHighlight.RED, AnsiSyntaxHighlight.GREEN };

		for (final String str : colors) {
			factory.addSyntaxHighlighterFactory(new SyntaxHighlighterFactory() {

				@Override
				public SyntaxHighlighter createSyntaxHighlighter(JsonGenerator generator) {
					return DefaultSyntaxHighlighter.newBuilder().withComma(str).build();
				}
			});
		}

		SyntaxHighlighter syntaxHighlighter = factory.createSyntaxHighlighter(null);
		assertTrue(syntaxHighlighter instanceof ListSyntaxHighlighter);
		assertEquals(AnsiSyntaxHighlight.build(AnsiSyntaxHighlight.RED, AnsiSyntaxHighlight.GREEN),
				syntaxHighlighter.forComma());
	}
}
