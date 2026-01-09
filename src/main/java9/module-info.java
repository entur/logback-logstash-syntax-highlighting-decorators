module org.entur.decorators {
	exports org.entur.decorators;
	exports org.entur.decorators.syntaxhighlight;
	exports org.entur.decorators.factory;

	requires tools.jackson.core;
	requires tools.jackson.databind;
	requires org.entur.jackson.tools.jsh;
	requires logstash.logback.encoder;
	requires org.apache.commons.text;
	requires org.slf4j;
}

