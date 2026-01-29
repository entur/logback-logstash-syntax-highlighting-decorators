package org.entur.decorators;



import org.entur.jackson.tools.jsh.AnsiSyntaxHighlight;
import org.entur.jackson.tools.jsh.DefaultSyntaxHighlighter;
import org.entur.jackson.tools.jsh.SyntaxHighlighter;
import org.junit.jupiter.api.Test;


import org.entur.decorators.factory.ListSyntaxHighlighterFactory;
import org.entur.decorators.factory.SyntaxHighlighterFactory;
import org.entur.decorators.syntaxhighlight.ListSyntaxHighlighter;

import static org.junit.jupiter.api.Assertions.*;

public class ListSyntaxHighlighterFactoryTest {

	@Test
	public void testSingle() {
		ListSyntaxHighlighterFactory factory = new ListSyntaxHighlighterFactory();

		factory.addSyntaxHighlighterFactory(new SyntaxHighlighterFactory() {

			@Override
			public SyntaxHighlighter createSyntaxHighlighter() {
				return DefaultSyntaxHighlighter.newBuilder().withComma(AnsiSyntaxHighlight.GREEN).build();
			}

		});

		SyntaxHighlighter syntaxHighlighter = factory.createSyntaxHighlighter();
		assertFalse(syntaxHighlighter instanceof ListSyntaxHighlighter);
		assertEquals(AnsiSyntaxHighlight.build(AnsiSyntaxHighlight.GREEN), syntaxHighlighter.forComma());
	}

	@Test
	public void testMultiple() {
		ListSyntaxHighlighterFactory factory = new ListSyntaxHighlighterFactory();

		String[] colors = new String[] { AnsiSyntaxHighlight.RED, AnsiSyntaxHighlight.GREEN };

		for (final String str : colors) {
			factory.addSyntaxHighlighterFactory(new SyntaxHighlighterFactory() {

				@Override
				public SyntaxHighlighter createSyntaxHighlighter() {
					return DefaultSyntaxHighlighter.newBuilder().withComma(str).build();
				}
			});
		}

		SyntaxHighlighter syntaxHighlighter = factory.createSyntaxHighlighter();
		assertTrue(syntaxHighlighter instanceof ListSyntaxHighlighter);
		assertEquals(AnsiSyntaxHighlight.build(AnsiSyntaxHighlight.RED, AnsiSyntaxHighlight.GREEN),
				syntaxHighlighter.forComma());
	}
}
