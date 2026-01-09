package org.entur.decorators;

import net.logstash.logback.decorate.MapperBuilderDecorator;
import org.entur.decorators.factory.ListSyntaxHighlighterFactory;
import org.entur.decorators.factory.LogLevelSyntaxHighlighterFactory;
import org.entur.decorators.factory.SyntaxHighlighterFactory;
import org.entur.jackson.tools.jsh.SyntaxHighlighter;
import org.entur.jackson.tools.jsh.SyntaxHighlightingPrettyPrinter;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.cfg.MapperBuilder;

/**
 * Enables syntax highlighting on a {@link MapperBuilder}.
 */
public class SyntaxHighlightingMapperBuilderDecorator<M extends ObjectMapper, B extends MapperBuilder<M, B>> extends ListSyntaxHighlighterFactory implements MapperBuilderDecorator<M, B> {

    protected SyntaxHighlighterFactory defaultSyntaxHighlighterFactory;
    protected boolean prettyPrint = true;

    public SyntaxHighlightingMapperBuilderDecorator(SyntaxHighlighterFactory defaultSyntaxHighlighterFactory) {
        this.defaultSyntaxHighlighterFactory = defaultSyntaxHighlighterFactory;
    }

    public SyntaxHighlightingMapperBuilderDecorator() {
        this(new LogLevelSyntaxHighlighterFactory());
    }

    @Override
    public B decorate(B mapperBuilder) {
        SyntaxHighlighter syntaxHighlighter = createSyntaxHighlighter();

        return mapperBuilder
                .enable(SerializationFeature.INDENT_OUTPUT)
                .defaultPrettyPrinter(new SyntaxHighlightingPrettyPrinter(syntaxHighlighter));
    }

    protected SyntaxHighlighter createDefaultSyntaxHighlighter() {
        return defaultSyntaxHighlighterFactory.createSyntaxHighlighter();
    }

    @Override
    public SyntaxHighlighter createSyntaxHighlighter() {
        if (factories.isEmpty()) {
            return createDefaultSyntaxHighlighter();
        }
        return super.createSyntaxHighlighter();
    }

    public void addSyntaxHighlighterFactory(SyntaxHighlighterFactory factory) {
        factories.add(factory);
    }

    public void setPrettyPrint(String prettyPrint) {
        this.prettyPrint = Boolean.parseBoolean(prettyPrint);
    }
}