package org.entur.decorators;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import org.entur.decorators.factory.ListJsonStreamContextListener;
import org.entur.decorators.factory.ListSyntaxHighlighterFactory;
import org.entur.decorators.factory.LogLevelSyntaxHighlighterFactory;
import org.entur.decorators.factory.SyntaxHighlighterFactory;
import org.entur.decorators.syntaxhighlight.ListSyntaxHighlighter;
import org.entur.jackson.jsh.JsonStreamContextListener;
import org.entur.jackson.jsh.SyntaxHighlighter;
import org.entur.jackson.jsh.SyntaxHighlightingJsonGenerator;

import net.logstash.logback.decorate.JsonGeneratorDecorator;

public class SyntaxHighlightingDecorator extends ListSyntaxHighlighterFactory implements JsonGeneratorDecorator {

	protected SyntaxHighlighterFactory defaultSyntaxHighlighterFactory;
	protected boolean prettyPrint = true;

	public SyntaxHighlightingDecorator(SyntaxHighlighterFactory defaultSyntaxHighlighterFactory) {
		this.defaultSyntaxHighlighterFactory = defaultSyntaxHighlighterFactory;
	}

	public SyntaxHighlightingDecorator() {
		this(new LogLevelSyntaxHighlighterFactory());
	}

	@Override
	public JsonGenerator decorate(JsonGenerator generator) {
		SyntaxHighlighter instance = createSyntaxHighlighter(generator);

		// check whether we need to add context listeners too
		if (instance instanceof ListSyntaxHighlighter) {
			ListSyntaxHighlighter list = (ListSyntaxHighlighter) instance;

			List<JsonStreamContextListener> listeners = new ArrayList<>(factories.size());
			for (SyntaxHighlighter h : list.getList()) {
				if (h instanceof JsonStreamContextListener) {
					listeners.add((JsonStreamContextListener) h);
				}
			}
			if (!listeners.isEmpty()) {
				if (listeners.size() == 1) {
					return new SyntaxHighlightingJsonGenerator(generator, instance, listeners.get(0), prettyPrint);
				}
				return new SyntaxHighlightingJsonGenerator(generator, instance,
						new ListJsonStreamContextListener(listeners), prettyPrint);
			}
		}

		if (instance instanceof JsonStreamContextListener) {
			return new SyntaxHighlightingJsonGenerator(generator, instance, (JsonStreamContextListener) instance,
					prettyPrint);
		}
		return new SyntaxHighlightingJsonGenerator(generator, instance, prettyPrint);

	}

	@Override
	public SyntaxHighlighter createSyntaxHighlighter(JsonGenerator generator) {
		if (factories.isEmpty()) {
			return createDefaultSyntaxHighlighter(generator);
		}
		return super.createSyntaxHighlighter(generator);
	}

	protected SyntaxHighlighter createDefaultSyntaxHighlighter(JsonGenerator generator) {
		return defaultSyntaxHighlighterFactory.createSyntaxHighlighter(generator);
	}

	public void addSyntaxHighlighterFactory(SyntaxHighlighterFactory factory) {
		factories.add(factory);
	}

	public void setPrettyPrint(String prettyPrint) {
		this.prettyPrint = Boolean.parseBoolean(prettyPrint);
	}

}
