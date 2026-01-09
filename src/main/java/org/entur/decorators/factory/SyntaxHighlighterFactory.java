package org.entur.decorators.factory;

import org.entur.jackson.tools.jsh.SyntaxHighlighter;

/**
 * Factory interface for {@linkplain org.entur.jackson.tools.jsh.SyntaxHighlighter}. <br>
 * <br>
 * Instances of the factory are required to be thread safe, the generated syntax
 * highlighters not.
 */

public interface SyntaxHighlighterFactory {

	/**
	 * Create {@linkplain SyntaxHighlighter} for coloring the input generator.
	 *
	 * @return highlighter a newly created syntax highlighter
	 */

	SyntaxHighlighter createSyntaxHighlighter();
}
