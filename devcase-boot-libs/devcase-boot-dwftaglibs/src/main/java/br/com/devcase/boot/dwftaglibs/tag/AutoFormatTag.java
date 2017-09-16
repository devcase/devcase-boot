package br.com.devcase.boot.dwftaglibs.tag;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.money.MonetaryAmount;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.servlet.support.RequestContextUtils;

public class AutoFormatTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5919591080850036801L;
	private Object value;
	private String format;
	private String locale;
	private String timeZone;

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

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	protected Locale locale() {
		if (this.locale != null) {
			return Locale.forLanguageTag(locale);
		} else {
			return RequestContextUtils.getLocale((HttpServletRequest) pageContext.getRequest());
		}
	}
	
	protected TimeZone timeZone() {
		if(this.timeZone != null) {
			return TimeZone.getTimeZone(this.timeZone);
		} else {
			//TODO
			return TimeZone.getDefault();
		}
	}

	protected static enum Format {
		STRING, BOOLEAN, MONEY, DATE, DATETIME, TIME;
	}

	@Override
	public int doStartTag() throws JspException {
		if (value == null)
			return SKIP_BODY;

		try {
			pageContext.getOut().write(getOutput(value));
		} catch (IOException e) {
			throw new JspTagException(e.toString(), e);
		}
		return SKIP_BODY;
	}
	
	protected String getOutput(Object value) {

		if (value instanceof Boolean) {
		} else if (value instanceof Calendar) {
			Instant instant = ((Calendar) value).toInstant();
			TimeZone timeZone = this.timeZone == null ? ((Calendar) value).getTimeZone() : timeZone();
			ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, timeZone.toZoneId());
			if(zdt.toLocalTime().equals(LocalTime.MIDNIGHT)) {
				return getOutput(zdt.toLocalDate());
			} else  {
				return getOutput(zdt);
			}
		} else if (value instanceof Date) {
			long epochMilli = ((Date) value).getTime();
			if(epochMilli > 0 && epochMilli < 24 * 60L * 60L * 1000L) {
				//Date está guardando apenas time
				return getOutput(LocalTime.ofSecondOfDay(epochMilli / 1000));
			}
			
			ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), timeZone().toZoneId());
			if(zdt.toLocalTime().equals(LocalTime.MIDNIGHT)) {
				return getOutput(zdt.toLocalDate());
			} else {
				return getOutput(zdt);
			}
		} else if (value instanceof LocalDate) {
			return getOutput((LocalDate) value);
		} else if (value instanceof LocalDateTime) {
			return getOutput((LocalDateTime) value);
		} else if (value instanceof ZonedDateTime) {
			return getOutput((ZonedDateTime) value);
		} else if (value instanceof LocalTime) {
			return getOutput((LocalDateTime) value);
		} else if (MonetaryAmount.class.isAssignableFrom(value.getClass())) {
			MonetaryAmount money = (MonetaryAmount) value;
			NumberFormat nf = NumberFormat.getCurrencyInstance(locale());
			nf.setCurrency(Currency.getInstance(money.getCurrency().getCurrencyCode()));
			return nf.format(money.getNumber());
		} 
		return value.toString();
	}
	protected String getOutput(LocalTime lt) {
		return lt.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).withLocale(locale()));
	}
	protected String getOutput(LocalDateTime ldt) {
		return ldt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(locale()));
	}
	protected String getOutput(ZonedDateTime zdt) {
		return zdt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(locale()));
	}
	protected String getOutput(LocalDate localDate) {
		return localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(locale()));
	}
}
