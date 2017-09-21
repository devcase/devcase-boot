package br.com.devcase.boot.beans.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

import org.javamoney.moneta.Money;
import org.springframework.util.StringUtils;

public class DwfMoneyEditor extends PropertyEditorSupport {
	final DecimalFormat df;

	public DwfMoneyEditor() {
		super();
		this.df = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));
	}

	@Override
	public String getAsText() {
		Money value = (Money) getValue();
		return value == null ? "" : value.toString(); //TODO
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(StringUtils.isEmpty(text)) {
			setValue(null);
		}
		
		//TODO - como fazer com o currency?
		try {
			setValue(Money.of(df.parse(text), "BRL"));
		} catch (ParseException e) {
			setValue(null);
		}
	}

}
