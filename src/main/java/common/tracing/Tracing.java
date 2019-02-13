package common.tracing;

import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.samplers.ConstSampler;

public final class Tracing {

	public Tracing() {
	}

	/**
	 * OpenTracing 初期化
	 * 
	 * @param service Service名
	 * @return Tracer
	 */
	public static JaegerTracer init(String service) {
		SamplerConfiguration samplerConfig = SamplerConfiguration.fromEnv().withType(ConstSampler.TYPE).withParam(1);

		ReporterConfiguration reporterConfig = ReporterConfiguration.fromEnv().withLogSpans(true);

		Configuration config = new Configuration(service).withSampler(samplerConfig).withReporter(reporterConfig);

		return config.getTracer();
	}
}
