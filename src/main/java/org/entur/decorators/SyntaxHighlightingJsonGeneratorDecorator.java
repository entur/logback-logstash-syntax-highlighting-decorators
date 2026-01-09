package org.entur.decorators;

import net.logstash.logback.decorate.JsonGeneratorDecorator;
import org.entur.decorators.factory.ListJsonStreamContextListener;
import org.entur.decorators.syntaxhighlight.ListSyntaxHighlighter;
import org.entur.jackson.tools.jsh.SyntaxHighlighter;
import org.entur.jackson.tools.jsh.SyntaxHighlightingJsonGenerator;
import org.entur.jackson.tools.jsh.SyntaxHighlightingPrettyPrinter;
import org.entur.jackson.tools.jsh.TokenStreamContextListener;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.PrettyPrinter;

import java.util.ArrayList;
import java.util.List;

// note: there is another JsonGeneratorDecorator in the Jackson library
public class SyntaxHighlightingJsonGeneratorDecorator implements JsonGeneratorDecorator {

    @Override
    public JsonGenerator decorate(JsonGenerator jsonGenerator) {

        PrettyPrinter candidate = jsonGenerator.getPrettyPrinter();
        if (candidate instanceof SyntaxHighlightingPrettyPrinter prettyPrinter) {

            SyntaxHighlighter instance = prettyPrinter.getSyntaxHighlighter();

            // check whether we need to add context listeners too
            if (instance instanceof ListSyntaxHighlighter) {
                ListSyntaxHighlighter list = (ListSyntaxHighlighter) instance;

                List<TokenStreamContextListener> listeners = new ArrayList<>();
                for (SyntaxHighlighter h : list.getList()) {
                    if (h instanceof TokenStreamContextListener) {
                        listeners.add((TokenStreamContextListener) h);
                    }
                }
                if (!listeners.isEmpty()) {
                    if (listeners.size() == 1) {
                        prettyPrinter.setTokenStreamContextListener(listeners.get(0));
                    } else {
                        prettyPrinter.setTokenStreamContextListener(new ListJsonStreamContextListener(listeners));
                    }
                }
            } else if(instance instanceof TokenStreamContextListener l) {
                prettyPrinter.setTokenStreamContextListener(l);
            }

            return new SyntaxHighlightingJsonGenerator(jsonGenerator, prettyPrinter, prettyPrinter.getObjectIndenter(), prettyPrinter.getArrayIndenter(), prettyPrinter.getSyntaxHighlighter());
        } else if(candidate != null) {
            throw new IllegalStateException("Expected pretty-printer of type " + SyntaxHighlightingPrettyPrinter.class.getName() + ", found " + candidate.getClass().getName() + ".");
        } else {
            throw new IllegalStateException("Expected pretty-printer of type " + SyntaxHighlightingPrettyPrinter.class.getName());
        }
    }
}
