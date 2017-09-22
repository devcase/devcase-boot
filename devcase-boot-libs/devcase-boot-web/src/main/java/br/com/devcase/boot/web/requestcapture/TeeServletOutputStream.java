package br.com.devcase.boot.web.requestcapture;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

import org.apache.commons.io.output.TeeOutputStream;

public class TeeServletOutputStream extends ServletOutputStream {
	private final ServletOutputStream original;
	private final TeeOutputStream teeOutputStream;
	public TeeServletOutputStream(ServletOutputStream original, OutputStream branch) {
		this.original = original;
		this.teeOutputStream = new TeeOutputStream(original, branch);
	}
	public int hashCode() {
		return teeOutputStream.hashCode();
	}
	public void write(byte[] b) throws IOException {
		teeOutputStream.write(b);
	}
	public void write(byte[] b, int off, int len) throws IOException {
		teeOutputStream.write(b, off, len);
	}
	public void write(int b) throws IOException {
		teeOutputStream.write(b);
	}
	public void flush() throws IOException {
		teeOutputStream.flush();
	}
	public void close() throws IOException {
		teeOutputStream.close();
	}
	public boolean equals(Object obj) {
		return teeOutputStream.equals(obj);
	}
	public String toString() {
		return teeOutputStream.toString();
	}
	@Override
	public boolean isReady() {
		return original.isReady();
	}
	@Override
	public void setWriteListener(WriteListener writeListener) {
		original.setWriteListener(writeListener);
	}
	
	
}