package br.com.devcase.boot.users.security.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import br.com.devcase.boot.users.security.social.SocialLoginConfiguration;

@Configuration
@ConditionalOnWebApplication
@EnableWebSecurity(debug=true)
@Import({ CommonSecurityConfig.class })
public class WebFormAuthenticationConfig {
	public static final int WEBFORM_SECURITY_ORDER = SecurityProperties.BASIC_AUTH_ORDER + 1;

	@Order(WEBFORM_SECURITY_ORDER)
	@Configuration
	@ConditionalOnMissingBean(value=SocialLoginConfiguration.SocialWebSecurityConfigurer.class)
	public static class WebFormSecurityConfigurer extends WebSecurityConfigurerAdapter {

		public WebFormSecurityConfigurer() {
			super(false);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.anyRequest().authenticated().and()
				.formLogin().loginPage("/login").permitAll().and()
				.logout().permitAll();
		}

		@Override
		public void init(WebSecurity web) throws Exception {
			super.init(web);
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
			return viewResolver.resolveViewName("login", localeResolver.resolveLocale(request));
		}

	}

}
