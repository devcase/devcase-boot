package br.com.devcase.boot.web.requestcapture;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptorAdapter;

class RequestCapturerInterceptor extends CallableProcessingInterceptorAdapter {
	private static Logger logger = LoggerFactory.getLogger(RequestCapturerInterceptor.class);
	
	private HttpBodyCollector logHolder;
	private HttpBodyCollectorPool pool;


	public RequestCapturerInterceptor(HttpBodyCollector logHolder, HttpBodyCollectorPool pool) {
		super();
		this.logHolder = logHolder;
		this.pool = pool;
	}


	@Override
	public <T> void postProcess(NativeWebRequest request, Callable<T> task, Object concurrentResult) throws Exception {
	}


	@Override
	public <T> void afterCompletion(NativeWebRequest request, Callable<T> task) throws Exception {
		logger.debug("(Assíncrono) Devolvendo o LogHolder ao pool");

		logHolder.requestComplete();
		pool.returnObject(logHolder);
	}
	
	
	
}