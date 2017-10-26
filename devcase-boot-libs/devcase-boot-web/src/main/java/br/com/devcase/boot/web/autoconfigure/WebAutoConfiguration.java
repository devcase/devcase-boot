package br.com.devcase.boot.web.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import br.com.devcase.boot.web.controllers.MessageSourceController;
import br.com.devcase.boot.web.editor.CustomPropertyEditorRegistrarAdvice;
import br.com.devcase.boot.web.i18n.LocaleChangeFilter;

@Configuration
public class WebAutoConfiguration {

	@Configuration
	@ConditionalOnWebApplication
	static class I18nConfiguration {
		@Bean
		@Autowired
		public FilterRegistrationBean<LocaleChangeFilter> localeChangeFilter(final LocaleResolver localeResolver) {
			return new FilterRegistrationBean<>(new LocaleChangeFilter(localeResolver));
		}

		@Bean
		public LocaleResolver localeResolver() {
			return new CookieLocaleResolver();
		}
		
		@Bean
		public MessageSourceController messageSourceController() {
			return new MessageSourceController();
		}
	}

	@Bean
	public CustomPropertyEditorRegistrarAdvice registerPropertyEditorAdvice() {
		return new CustomPropertyEditorRegistrarAdvice();
	}
}
