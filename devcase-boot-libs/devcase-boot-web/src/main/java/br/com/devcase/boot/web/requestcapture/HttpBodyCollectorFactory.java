package br.com.devcase.boot.web.requestcapture;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class HttpBodyCollectorFactory extends BasePoolableObjectFactory<HttpBodyCollector> {
	@Autowired
	private RequestCaptureListener restLogger;

	@Override
	public HttpBodyCollector makeObject() throws Exception {
		return new HttpBodyCollector();
	}

	@Override
	public void activateObject(HttpBodyCollector obj) throws Exception {
		obj.getRequestBody().clear();
		obj.getResponseBody().clear();
		obj.setRequestCharset(null);
		obj.setResponseCharset(null);
		obj.setRestLogger(restLogger);
	}

	
	
}