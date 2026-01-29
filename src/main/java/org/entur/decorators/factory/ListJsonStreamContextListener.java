package org.entur.decorators.factory;

import org.entur.jackson.tools.jsh.TokenStreamContextListener;
import tools.jackson.core.TokenStreamContext;

import java.util.ArrayList;
import java.util.List;

public class ListJsonStreamContextListener implements TokenStreamContextListener {

	protected List<TokenStreamContextListener> listeners;

	public ListJsonStreamContextListener(List<TokenStreamContextListener> listeners) {
		this.listeners = listeners;
	}

	public ListJsonStreamContextListener() {
		this(new ArrayList<TokenStreamContextListener>());
	}

	@Override
	public void startObject(TokenStreamContext outputContext) {
		for (TokenStreamContextListener listener : listeners) {
			listener.startObject(outputContext);
		}
	}

	@Override
	public void endObject(TokenStreamContext outputContext) {
		for (TokenStreamContextListener listener : listeners) {
			listener.endObject(outputContext);
		}
	}

	@Override
	public void startArray(TokenStreamContext outputContext) {
		for (TokenStreamContextListener listener : listeners) {
			listener.startArray(outputContext);
		}
	}

	@Override
	public void endArray(TokenStreamContext outputContext) {
		for (TokenStreamContextListener listener : listeners) {
			listener.endArray(outputContext);
		}
	}

}
