package lesson01;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

import io.jaegertracing.internal.JaegerTracer;
import io.opentracing.Span;
import io.opentracing.Tracer;

public class Hello {
	final Logger logger = LoggerFactory.getLogger(Hello.class);
    private final Tracer tracer;
    
    private Hello(Tracer tracer) {
    	this.tracer = tracer;
    }
	
	public static void main(String[] args) {
		
	}

}
