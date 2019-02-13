package lesson01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

import common.tracing.Tracing;
import io.jaegertracing.internal.JaegerTracer;
import io.opentracing.Span;
import io.opentracing.Tracer;

public class Hello {
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Tracer tracer;

	private Hello(Tracer tracer) {
		this.tracer = tracer;
	}

	private void sayHello(String helloTo) {
		Span span = tracer.buildSpan("say-hello").start(); // Span 開始
		span.setTag("hello-to", helloTo); // Tagを設定

		String helloStr = String.format("Hello, %s!", helloTo);
		span.log(ImmutableMap.of("event", "string-format", "value", helloStr)); // Span log

		System.out.println(helloStr);
		span.log(ImmutableMap.of("event", "println"));

		span.finish();
	}

	public static void main(String[] args) {

		if (args.length != 1) {
			throw new IllegalArgumentException("Expecting one argument");
		}
		String helloTo = args[0];

		JaegerTracer tracer;
		Hello hello;
		try {
			tracer = Tracing.init("hello-world");
		} catch (Exception e) {
			return;
		}

		hello = new Hello(tracer);
		hello.sayHello(helloTo);
		hello.logger.info("Finish");

	}

}
