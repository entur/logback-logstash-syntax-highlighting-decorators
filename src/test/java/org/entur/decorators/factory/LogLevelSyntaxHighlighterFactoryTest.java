package org.entur.decorators.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringWriter;

import org.entur.decorators.factory.LogLevelSyntaxHighlighterFactory.Level;
import org.entur.decorators.syntaxhighlight.LogLevelSyntaxHighlighter;
import org.entur.jackson.tools.jsh.SyntaxHighlighter;
import org.junit.jupiter.api.Test;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.json.JsonFactory;

public class LogLevelSyntaxHighlighterFactoryTest {
	
	@Test
	public void testLogLevelSyntaxHighlighterFactory() throws Exception {
		LogLevelSyntaxHighlighterFactory factory = new LogLevelSyntaxHighlighterFactory();
		factory.setMessage("black");
		
		Level level = new LogLevelSyntaxHighlighterFactory.Level();
		level.setDebug("black");
		level.setInfo("blue");
		level.setWarning("yellow");
		level.setError("red");
		
		factory.setLevel(level);
		
		SyntaxHighlighter syntaxHighlighter = factory.createSyntaxHighlighter();
		assertTrue(syntaxHighlighter instanceof LogLevelSyntaxHighlighter);

	}

}
