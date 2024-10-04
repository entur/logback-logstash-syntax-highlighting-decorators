module org.entur.decorators {
	exports org.entur.decorators;
	exports org.entur.decorators.syntaxhighlight;
	exports org.entur.decorators.factory;

	requires com.fasterxml.jackson.core;
	requires org.entur.jackson.jsh;
	requires logstash.logback.encoder;
	requires org.apache.commons.text;
	requires org.slf4j;
}

