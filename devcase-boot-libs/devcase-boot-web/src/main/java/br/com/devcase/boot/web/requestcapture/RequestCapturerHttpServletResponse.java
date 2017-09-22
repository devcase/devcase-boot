package br.com.devcase.boot.web.requestcapture;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

class RequestCapturerHttpServletResponse extends HttpServletResponseWrapper {
	private TeeServletOutputStream teeServletOutputStream;
	public RequestCapturerHttpServletResponse(HttpServletResponse response, HttpBodyCollector logHolder) throws IOException {
		super(response);
		this.teeServletOutputStream = new TeeServletOutputStream(response.getOutputStream(), new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				if(logHolder.getResponseBody().hasRemaining())
					logHolder.getResponseBody().put((byte) b);
			}
		});
	}
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return teeServletOutputStream;
	}
	
	
}