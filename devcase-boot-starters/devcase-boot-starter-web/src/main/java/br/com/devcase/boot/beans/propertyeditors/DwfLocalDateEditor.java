package br.com.devcase.boot.beans.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.util.StringUtils;

public class DwfLocalDateEditor extends PropertyEditorSupport {
	final DateTimeFormatter formatter;

	public DwfLocalDateEditor() {
		super();
		this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	}

	@Override
	public String getAsText() {
		LocalDate value = (LocalDate) getValue();
		return value == null ? "" : value.format(formatter);
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(StringUtils.isEmpty(text)) {
			setValue(null);
		}
		else {
			setValue(LocalDate.parse(text, formatter));
		}
	}

}
