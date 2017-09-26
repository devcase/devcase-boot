package br.com.devcase.boot.users.security.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import br.com.devcase.boot.users.security.DefaultUserDetailsManager;

@Configuration
public class DevcaseUsersSecurityAutoConfiguration {

	@Configuration
	static class SecurityComponentsConfig {
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Bean
		public UserDetailsManager defaultUserDetailsManager() {
			return new DefaultUserDetailsManager();
		}
	}

}
