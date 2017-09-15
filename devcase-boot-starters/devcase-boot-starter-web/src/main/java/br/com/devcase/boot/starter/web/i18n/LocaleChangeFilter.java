package br.com.devcase.boot.starter.web.i18n;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;

public class LocaleChangeFilter extends OncePerRequestFilter {
	private final LocaleResolver localeResolver;

	public LocaleChangeFilter(LocaleResolver localeResolver) {
		super();
		Assert.notNull(localeResolver, "Locale Resolver é obrigatório");
		this.localeResolver = localeResolver;
	}

	String getParamName() {
		return "locale";
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// habilita localeResolver para servlets e jsps fora do spring mvc
		request.setAttribute(DispatcherServlet.LOCALE_RESOLVER_ATTRIBUTE, this.localeResolver);

		String newLocale = request.getParameter(getParamName());
		if (newLocale != null) {
			try {
				localeResolver.setLocale(request, response, parseLocaleValue(newLocale));
			} catch (IllegalArgumentException ex) {
				// ignore
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

	protected Locale parseLocaleValue(String locale) {
		return Locale.forLanguageTag(locale);
	}

}
