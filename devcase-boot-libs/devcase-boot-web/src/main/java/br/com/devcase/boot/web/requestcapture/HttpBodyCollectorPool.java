package br.com.devcase.boot.web.requestcapture;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

public class HttpBodyCollectorPool extends GenericObjectPool<HttpBodyCollector> {
	public HttpBodyCollectorPool() {
		super();
	}

	public HttpBodyCollectorPool(PoolableObjectFactory<HttpBodyCollector> factory, int maxActive) {
		super(factory, maxActive);
	}

	public HttpBodyCollectorPool(PoolableObjectFactory<HttpBodyCollector> factory) {
		super(factory);
	}
}