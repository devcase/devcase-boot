package br.com.devcase.boot.users.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.devcase.boot.users.security.userdetails.DefaultUserDetailsService;

@Configuration
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
	

}
