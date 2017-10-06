package br.com.devcase.boot.users.security.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.SpringBootWebSecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.google.common.io.ByteStreams;
import com.google.common.primitives.Bytes;

@Configuration
@ConditionalOnWebApplication
@Import({CommonSecurityConfig.class, SpringBootWebSecurityConfiguration.class})
public class WebFormAuthenticationConfig  {
	public static final int WEBFORM_SECURITY_ORDER = SecurityProperties.ACCESS_OVERRIDE_ORDER;
	
	@Order(WEBFORM_SECURITY_ORDER)
	@Configuration
	static class SecurityConfigurer extends WebSecurityConfigurerAdapter {
		
		public SecurityConfigurer() {
			super(false);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
					.anyRequest().authenticated()
					.and()
				.formLogin()
					.loginPage("/login")
					.permitAll();
		}
	}
	
	@Controller
	@ConditionalOnClass(value=HttpServletRequest.class)
	@Lazy
	static class FormLoginController {
		
		@Value("${devcase-boot.users.security.login-form-file:classpath:/defaultLogin.html}")
		Resource loginFormResource;
		@Value("${devcase-boot.users.security.login-form-file.charset:ISO-8859-1}")
		String charset;
		
		byte[] pageContents;
		int csrfNamePos;
		int csrfNameSize;
		int csrfTokenPos;
		int csrfTokenSize;
		int errorMessagePos;
		int errorMessageSize;
		
		@PostConstruct
		void parseLoginForm() throws IOException {
			try (InputStream is = loginFormResource.getInputStream()) {
				pageContents = ByteStreams.toByteArray(is);
				Charset charset = Charset.forName(this.charset);

				csrfNamePos = Bytes.indexOf(pageContents, "@csrfname@".getBytes(charset));
				csrfNameSize = "@csrfname@".getBytes(charset).length;

				csrfTokenPos = Bytes.indexOf(pageContents, "@csrftoken@".getBytes(charset));
				csrfTokenSize = "@csrftoken@".getBytes(charset).length;

				errorMessagePos = Bytes.indexOf(pageContents, "@errormessage@".getBytes(charset));
				errorMessageSize = "@errormessage@".getBytes(charset).length;

			}
		}
		
		View loginForm = new View() {
			@Override
			public String getContentType() {
				return "text/html";
			}
			
			@Override
			public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
				CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
				Charset charset2 = Charset.forName(charset);

				response.setContentType(getContentType());
				try (OutputStream os = response.getOutputStream()) {
					os.write(pageContents, 0, csrfNamePos);
					//name
					if (token != null) {
						os.write(token.getParameterName().getBytes(charset2));
					}
					os.write(pageContents, csrfNamePos + csrfNameSize, csrfTokenPos - csrfNamePos - csrfNameSize);
					if (token != null) {
						os.write(token.getToken().getBytes(charset2));
					}
					os.write(pageContents, csrfTokenPos + csrfTokenSize, errorMessagePos - csrfTokenPos - csrfTokenSize);

					HttpSession session = request.getSession(false);
					if (session != null) {
						AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
						String errorMsg = ex != null ? ex.getMessage() : "";
						errorMsg = StringEscapeUtils.escapeEcmaScript(errorMsg);
						os.write(errorMsg.getBytes(charset2));
					}
					
					os.write(pageContents, errorMessagePos + errorMessageSize, pageContents.length - errorMessagePos - errorMessageSize);

				}
			}
		};
		
		@RequestMapping("/login")
		View loginForm() {
			return loginForm;
		}
		
	}

	
}
