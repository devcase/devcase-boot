package br.com.devcase.boot.starter.web;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebAutoConfiguration {

	static class I18nConfiguration {
		@Bean
		@Autowired
		public FilterRegistrationBean localeChangeFilter(final LocaleResolver localeResolver) {
			return new FilterRegistrationBean(new Filter() {
				
				String getParamName() {
					return "locale";
				}
				@Override
				public void init(FilterConfig filterConfig) throws ServletException {
				}

				@Override
				public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
						throws IOException, ServletException {
					String newLocale = request.getParameter(getParamName());
					
					if (newLocale != null) {
						try {
							localeResolver.setLocale((HttpServletRequest) request, (HttpServletResponse) response, parseLocaleValue(newLocale));
						} catch (IllegalArgumentException ex) {
							//ignore
						}
					}
					
					chain.doFilter(request, response);
				}

				@Override
				public void destroy() {
				}
				
				protected Locale parseLocaleValue(String locale) {
					 return Locale.forLanguageTag(locale);
				}
			});
		}

		@Bean
		public LocaleResolver localeResolver() {
			return new SessionLocaleResolver();
		}

	}

}
