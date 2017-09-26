package br.com.devcase.boot.users.security.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.devcase.boot.users.security.DefaultUserDetailsService;
import br.com.devcase.boot.users.security.repositories.UserReadOnlyRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses=UserReadOnlyRepository.class)
public class DevcaseUsersSecurityAutoConfiguration {

	@Configuration
	static class SecurityComponentsConfig {
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Bean
		public UserDetailsService defaultUserDetailsService() {
			return new DefaultUserDetailsService();
		}
	}

}
