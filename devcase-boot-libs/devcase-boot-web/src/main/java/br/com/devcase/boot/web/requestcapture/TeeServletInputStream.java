package br.com.devcase.boot.web.requestcapture;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

import org.apache.commons.io.input.TeeInputStream;

public class TeeServletInputStream extends ServletInputStream {
	private final ServletInputStream original;
	private final TeeInputStream teeInputStream;
	public TeeServletInputStream(ServletInputStream original, OutputStream branch) {
		super();
		this.original = original;
		this.teeInputStream = new TeeInputStream(original, branch);
	}
	public int hashCode() {
		return teeInputStream.hashCode();
	}
	public void close() throws IOException {
		teeInputStream.close();
	}
	public boolean equals(Object obj) {
		return teeInputStream.equals(obj);
	}
	public int read() throws IOException {
		return teeInputStream.read();
	}
	public long skip(long ln) throws IOException {
		return teeInputStream.skip(ln);
	}
	public int read(byte[] bts, int st, int end) throws IOException {
		return teeInputStream.read(bts, st, end);
	}
	public int available() throws IOException {
		return teeInputStream.available();
	}
	public int read(byte[] bts) throws IOException {
		return teeInputStream.read(bts);
	}
	public void mark(int readlimit) {
		teeInputStream.mark(readlimit);
	}
	public void reset() throws IOException {
		teeInputStream.reset();
	}
	public boolean markSupported() {
		return teeInputStream.markSupported();
	}
	public String toString() {
		return teeInputStream.toString();
	}
	public int readLine(byte[] b, int off, int len) throws IOException {
		return original.readLine(b, off, len);
	}
	public boolean isFinished() {
		return original.isFinished();
	}
	public boolean isReady() {
		return original.isReady();
	}
	public void setReadListener(ReadListener readListener) {
		original.setReadListener(readListener);
	}
}
