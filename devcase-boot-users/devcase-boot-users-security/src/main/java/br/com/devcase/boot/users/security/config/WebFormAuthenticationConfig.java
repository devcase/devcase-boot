package br.com.devcase.boot.users.security.config;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.csrf.CsrfToken;
//import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@ConditionalOnWebApplication
@EnableWebSecurity()
@Import({ CommonSecurityConfig.class })
public class WebFormAuthenticationConfig {
	/**
	 * Após {@link org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration}
	 */
	public static final int WEBFORM_SECURITY_ORDER = 5; 

	@Order(WEBFORM_SECURITY_ORDER)
	@Configuration
//	@ConditionalOnMissingBean(value= { SocialWebSecurityConfigurer.class })
	public static class WebFormSecurityConfigurer extends WebSecurityConfigurerAdapter {
		private Logger logger = LoggerFactory.getLogger(getClass());

		public WebFormSecurityConfigurer() {
			super(false);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			logger.debug("Configuring http for webform");
			http.authorizeRequests()
				.anyRequest().authenticated().and()
				.formLogin().loginPage("/login").permitAll().and()
				.logout().permitAll();
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/**/favicon.ico");
		}

	}

	@Controller
	@ConditionalOnClass(value = HttpServletRequest.class)
	@Lazy
	static class FormLoginController {

		@Autowired
		private ThymeleafViewResolver viewResolver;
		@Autowired
		private LocaleResolver localeResolver;
//		@Autowired(required=false)
//		private ConnectionFactoryLocator connectionFactoryLocator;

		@RequestMapping("/login")
		View loginForm(HttpServletRequest request, Model model) throws Exception {
			CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
			model.addAttribute("csrfToken", token);

			HttpSession session = request.getSession(false);
			if (session != null) {
				AuthenticationException ex = (AuthenticationException) session
						.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
				String errorMsg = ex != null ? ex.getMessage() : "";
				errorMsg = StringEscapeUtils.escapeEcmaScript(errorMsg);
				model.addAttribute("authenticationErrorMessage", errorMsg);
			}
			
//			if(connectionFactoryLocator != null) {
//				model.addAttribute("registeredProviderIds", connectionFactoryLocator.registeredProviderIds());
//			} else {
				model.addAttribute("registeredProviderIds", Collections.EMPTY_SET);
//			}
			return viewResolver.resolveViewName("login", localeResolver.resolveLocale(request));
		}

	}

}
