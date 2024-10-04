package org.entur.decorators.factory;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import org.entur.decorators.syntaxhighlight.ListSyntaxHighlighter;
import org.entur.jackson.jsh.SyntaxHighlighter;

public class ListSyntaxHighlighterFactory implements SyntaxHighlighterFactory {

	protected List<SyntaxHighlighterFactory> factories = new ArrayList<>();

	public void addSyntaxHighlighterFactory(SyntaxHighlighterFactory factory) {
		factories.add(factory);
	}

	public SyntaxHighlighter createSyntaxHighlighter(JsonGenerator generator) {
		if (factories.size() == 1) {
			return factories.get(0).createSyntaxHighlighter(generator);
		}

		List<SyntaxHighlighter> highlighterList = new ArrayList<>(factories.size());

		for (SyntaxHighlighterFactory factory : factories) {
			highlighterList.add(factory.createSyntaxHighlighter(generator));
		}

		return new ListSyntaxHighlighter(highlighterList);
	}

}
