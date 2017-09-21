package br.com.devcase.boot.web.editor;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.javamoney.moneta.Money;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import br.com.devcase.boot.beans.propertyeditors.DwfLocalDateEditor;
import br.com.devcase.boot.beans.propertyeditors.DwfMoneyEditor;

/**
 * Configura propertyEditor customizados específicos para Controllers
 * @author hirata
 *
 */
@ControllerAdvice
public class CustomPropertyEditorRegistrarAdvice {
	@InitBinder
	public void bindingPreparation(WebDataBinder binder, HttpServletRequest request) {
//		binder.setAutoGrowCollectionLimit(336); // for dwf:inputDayOfWeekTimeSchedule
//		Locale locale = RequestContextUtils.getLocale(request);
//		DecimalFormat df = new DecimalFormat("#,##0.0", new DecimalFormatSymbols(locale));
//		binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, df, true));
//		binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, df, true));
//		binder.registerCustomEditor(Float.class, new CustomNumberEditor(Float.class, df, true));
//		binder.registerCustomEditor(float.class, new CustomNumberEditor(Float.class, df, true));
//		binder.registerCustomEditor(double.class, new CustomNumberEditor(Double.class, df, true));
//		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, df, true));
//		binder.registerCustomEditor(int.class, new CustomNumberEditor(Integer.class, df, true));

		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true)); // string vazias viram nulll
		binder.registerCustomEditor(LocalDate.class, new DwfLocalDateEditor()); //inputDate.tag
		binder.registerCustomEditor(Money.class, new DwfMoneyEditor()); //inputMoney.tag

	}

}
