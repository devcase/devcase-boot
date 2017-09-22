package br.com.devcase.boot.web.requestcapture;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.async.WebAsyncManager;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class RequestCapturerHttpFilter extends OncePerRequestFilter {
	private static Logger logger = LoggerFactory.getLogger(RequestCapturerHttpFilter.class);
	
	@Autowired
	private HttpBodyCollectorPool httpBodyCollectorPool;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		HttpBodyCollector logHolder = null;
		try {
			logger.debug("Recuperando LogHolder do pool");
			logHolder = httpBodyCollectorPool.borrowObject();
			logHolder.setStartTime(System.currentTimeMillis());
			logHolder.setRequest(request);
			logHolder.setResponse(response);
			request = new RequestCapturerHttpServletRequest(request, logHolder);
			response = new RequestCapturerHttpServletResponse(response, logHolder);
			
			WebAsyncManager wam = WebAsyncUtils.getAsyncManager(request);
			wam.registerCallableInterceptors(new RequestCapturerInterceptor(logHolder, httpBodyCollectorPool));
		} catch (Exception e) {
			logger.warn("Não consegui criar o LogHolder", e);
		}

		try {
			filterChain.doFilter(request, response);
		} finally {
			if (!isAsyncStarted(request) && logHolder != null) {
				logger.debug("(Não assíncrono) Devolvendo o LogHolder ao pool");
				try {
					logHolder.requestComplete();
					httpBodyCollectorPool.returnObject(logHolder);
				} catch (Exception e) {
					logger.debug("Erro devolvendo o holder ao pool", e);
				}
			}
		}
	}
}
