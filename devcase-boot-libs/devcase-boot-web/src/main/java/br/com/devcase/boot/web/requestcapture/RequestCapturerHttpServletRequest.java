package br.com.devcase.boot.web.requestcapture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

class RequestCapturerHttpServletRequest extends HttpServletRequestWrapper {
	
	private HttpBodyCollector logHolder;
	private TeeServletInputStream teeInputStream;
	public RequestCapturerHttpServletRequest(HttpServletRequest request, HttpBodyCollector logHolder) throws IOException {
		super(request);
		this.logHolder = logHolder;
		this.teeInputStream = new TeeServletInputStream(request.getInputStream(), new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				if(logHolder.getRequestBody().hasRemaining())
					logHolder.getRequestBody().put((byte) b);
			}
		});
	}
	@Override
	public ServletInputStream getInputStream() throws IOException {
		return teeInputStream;
	}
	@Override
	public void setCharacterEncoding(String enc) throws UnsupportedEncodingException {
		logHolder.setRequestCharset(enc);
		super.setCharacterEncoding(enc);
	}
	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream(), getCharacterEncoding()));
	}
	
	
	
	
}