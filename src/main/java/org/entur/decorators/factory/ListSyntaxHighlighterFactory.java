package org.entur.decorators.factory;

import org.entur.decorators.syntaxhighlight.ListSyntaxHighlighter;
import org.entur.jackson.tools.jsh.SyntaxHighlighter;

import java.util.ArrayList;
import java.util.List;


public class ListSyntaxHighlighterFactory implements SyntaxHighlighterFactory {

	protected List<SyntaxHighlighterFactory> factories = new ArrayList<>();

	public void addSyntaxHighlighterFactory(SyntaxHighlighterFactory factory) {
		factories.add(factory);
	}

	public SyntaxHighlighter createSyntaxHighlighter() {
		if (factories.size() == 1) {
			return factories.get(0).createSyntaxHighlighter();
		}

		List<SyntaxHighlighter> highlighterList = new ArrayList<>(factories.size());

		for (SyntaxHighlighterFactory factory : factories) {
			highlighterList.add(factory.createSyntaxHighlighter());
		}

		return new ListSyntaxHighlighter(highlighterList);
	}

}
