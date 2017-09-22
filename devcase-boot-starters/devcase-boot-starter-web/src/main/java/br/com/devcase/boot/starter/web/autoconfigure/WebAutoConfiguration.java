package br.com.devcase.boot.starter.web.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import br.com.devcase.boot.web.editor.CustomPropertyEditorRegistrarAdvice;
import br.com.devcase.boot.web.i18n.LocaleChangeFilter;

@Configuration
public class WebAutoConfiguration {

	static class I18nConfiguration {
		@Bean
		@Autowired
		public FilterRegistrationBean localeChangeFilter(final LocaleResolver localeResolver) {
			return new FilterRegistrationBean(new LocaleChangeFilter(localeResolver));
		}

		@Bean
		public LocaleResolver localeResolver() {
			return new CookieLocaleResolver();
		}
	}

	@Bean
	public CustomPropertyEditorRegistrarAdvice registerPropertyEditorAdvice() {
		return new CustomPropertyEditorRegistrarAdvice();
	}
}
