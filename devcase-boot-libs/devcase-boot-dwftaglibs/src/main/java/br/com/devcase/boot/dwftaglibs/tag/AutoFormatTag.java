package br.com.devcase.boot.dwftaglibs.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class AutoFormatTag extends SimpleTagSupport {

	private Object value;
	private String format;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	protected static enum Format {
		STRING, BOOLEAN, MONEY;
	}

	@Override
	public void doTag() throws JspException, IOException {
		if (value == null)
			return;

	}
	
	protected String getOutput() {
		Format f = Format.STRING;

		if (value instanceof Boolean) {
			f = Format.BOOLEAN;
		}

		switch (f) {
		default: 
			 return value.toString();
		}
	}

}
