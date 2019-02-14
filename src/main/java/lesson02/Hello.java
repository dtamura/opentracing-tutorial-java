package lesson02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

import common.tracing.Tracing;
import io.jaegertracing.internal.JaegerTracer;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;

public class Hello {
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Tracer tracer;

	private Hello(Tracer tracer) {
		this.tracer = tracer;
	}

	private void sayHello(String helloTo) {
//		Span span = tracer.buildSpan("say-hello").start(); // Span 開始 (Lesson01)
		try (Scope scope = tracer.buildSpan("say-hello").startActive(true)) { // Span開始
//		span.setTag("hello-to", helloTo); // Tagを設定(Lesson01)
			scope.span().setTag("hello-to", helloTo); // Tag設定

			String helloStr = formatString(helloTo);
			printHello(helloStr);
		}

	}

	private String formatString(String helloTo) {
		try (Scope scope = tracer.buildSpan("formatString").startActive(true)) {
			String helloStr = String.format("Hello, %s!", helloTo);
			scope.span().log(ImmutableMap.of("event", "string-format", "value", helloStr));
			return helloStr;
		}
	}

	private void printHello(String helloStr) {
		try (Scope scope = tracer.buildSpan("printHello").startActive(true)) {
			System.out.println(helloStr);
			scope.span().log(ImmutableMap.of("event", "println"));
		}
	}

	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger("main");
		logger.info("Lesson02 Start");
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
