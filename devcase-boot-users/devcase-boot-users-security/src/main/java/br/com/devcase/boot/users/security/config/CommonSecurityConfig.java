package br.com.devcase.boot.users.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.devcase.boot.crud.jpa.repository.support.CriteriaJpaRepository;
import br.com.devcase.boot.users.security.repositories.UserReadOnlyRepository;
import br.com.devcase.boot.users.security.userdetails.DefaultUserDetailsService;

@Configuration
@EnableJpaRepositories(basePackageClasses = UserReadOnlyRepository.class, repositoryBaseClass=CriteriaJpaRepository.class)
@EnableGlobalAuthentication()
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class CommonSecurityConfig {

	@Configuration
	static class SecurityComponentsConfig extends GlobalAuthenticationConfigurerAdapter {
		@Bean
		@Primary
		public UserDetailsService defaultUserDetailsService() {
			return new DefaultUserDetailsService();
		}

		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(defaultUserDetailsService()).passwordEncoder(passwordEncoder());
		}
	}

	@Configuration
	@AutoConfigureAfter(value=AuthenticationConfiguration.class)
	static class AuthenticationManagerConfig {
		@Bean
		@Autowired
		@Primary
		public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
			AuthenticationManager am = builder.getAuthenticationManager();
			return am;
		}
	}
	
	@Order(WebFormAuthenticationConfig.WEBFORM_SECURITY_ORDER - 1)
	@Configuration
	public static class ApiWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/api/**")
				.authorizeRequests().anyRequest().permitAll().and()
				.formLogin().disable()
				.httpBasic().disable();
		}
		
		
	}
	

}
