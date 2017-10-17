package br.com.devcase.boot.users.webadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import br.com.devcase.boot.crud.jpa.repository.support.CriteriaJpaRepository;
import br.com.devcase.boot.users.security.config.WebFormAuthenticationConfig;
import br.com.devcase.boot.users.security.social.EnableSocialLogin;

@SpringBootApplication
//@EnableWebFormAuthentication
@EnableSocialLogin
@Import(UsersWebAdminConfiguration.class)
public class UsersWebAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersWebAdminApplication.class, args);
	}

	@Order(WebFormAuthenticationConfig.WEBFORM_SECURITY_ORDER - 1)
	@Configuration
	public static class ApiWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/api/**")
				.authorizeRequests().anyRequest().hasRole("ADMIN_USERS").and()
				.formLogin().disable()
				.httpBasic().disable();
		}
	}
	

}
