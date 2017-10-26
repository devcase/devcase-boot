package br.com.devcase.boot.users.webadmin;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.devcase.boot.crud.jpa.repository.support.CriteriaJpaRepository;
import br.com.devcase.boot.users.repositories.CredentialRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses=CredentialRepository.class, repositoryBaseClass=CriteriaJpaRepository.class)
@PropertySource("classpath:/br/com/devcase/boot/users/webadmin/webadmin.properties")
@ComponentScan(basePackageClasses=CredentialRepository.class)
public class UsersWebAdminConfiguration {

	@Bean
	@ConditionalOnMissingBean(PasswordEncoder.class)
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
