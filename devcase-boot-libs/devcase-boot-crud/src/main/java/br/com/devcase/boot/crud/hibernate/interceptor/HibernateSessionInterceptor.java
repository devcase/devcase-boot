package br.com.devcase.boot.crud.hibernate.interceptor;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.event.spi.SaveOrUpdateEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateSessionInterceptor implements SaveOrUpdateEventListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(HibernateSessionInterceptor.class);

	public HibernateSessionInterceptor() {
		super();
		new RuntimeException().printStackTrace();
	}


	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
		LOGGER.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + event);
		new RuntimeException().printStackTrace();
		
	}

}
