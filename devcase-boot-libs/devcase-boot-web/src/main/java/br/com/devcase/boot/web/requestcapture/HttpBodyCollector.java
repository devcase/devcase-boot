package br.com.devcase.boot.web.requestcapture;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Objeto base que vai armazenar os dados do corpo das mensagens
 * de uma chamada http
 * @author hirata
 *
 */
public class HttpBodyCollector {
	private static Logger logger = LoggerFactory.getLogger(HttpBodyCollector.class);
	
	private long startTime;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ByteBuffer requestBody;
	private String requestCharset;
	private ByteBuffer responseBody;
	private String responseCharset;
	private RequestCaptureListener restLogger;

	public HttpBodyCollector() {
		this.requestBody = ByteBuffer.allocate(10000);
		this.responseBody = ByteBuffer.allocate(10000);
	}
	
	public void requestComplete() {
		String requestString = extractToString(requestBody, requestCharset);
		String responseString = extractToString(responseBody, responseCharset);
		requestBody.clear();
		responseBody.clear();
		try {
			restLogger.requestCaptured(request, response, requestString, responseString, startTime);
		} catch(Exception ex) {
			logger.error("Erro gravando LOG", ex);
		}
	}
	
	private String extractToString(ByteBuffer buffer, String charset) {
		byte[] bytes = Arrays.copyOf(buffer.array(), buffer.position());
		
		try {
			return (new String(bytes, charset != null ? charset : "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			try {
				return (new String(bytes, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				return new String(bytes);
			}
		}
	}
	
	
	public ByteBuffer getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(ByteBuffer request) {
		this.requestBody = request;
	}

	public String getRequestCharset() {
		return requestCharset;
	}

	public void setRequestCharset(String requestCharset) {
		this.requestCharset = requestCharset;
	}

	public ByteBuffer getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(ByteBuffer response) {
		this.responseBody = response;
	}

	public String getResponseCharset() {
		return responseCharset;
	}

	public void setResponseCharset(String responseCharset) {
		this.responseCharset = responseCharset;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public RequestCaptureListener getRestLogger() {
		return restLogger;
	}

	public void setRestLogger(RequestCaptureListener restLogger) {
		this.restLogger = restLogger;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	

}