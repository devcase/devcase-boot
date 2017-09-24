package br.com.devcase.boot.web.requestcapture;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestCaptureListener {
	void requestCaptured(HttpServletRequest request, HttpServletResponse response, String requestBody, String responseBody, long requestStartTime) throws IOException ;
}
