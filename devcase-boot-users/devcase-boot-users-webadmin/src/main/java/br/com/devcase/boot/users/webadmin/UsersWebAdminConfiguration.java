package br.com.devcase.boot.users.webadmin;

import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.devcase.boot.crud.jpa.repository.support.CriteriaJpaRepository;
import br.com.devcase.boot.users.repositories.CredentialRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses=CredentialRepository.class, repositoryBaseClass=CriteriaJpaRepository.class)
@PropertySource("classpath:/br/com/devcase/boot/users/webadmin/webadmin.properties")
@ComponentScan(basePackageClasses=CredentialRepository.class)
public class UsersWebAdminConfiguration {

	@Configuration
	static class ExposeIds extends RepositoryRestConfigurerAdapter {
		@Autowired
		private EntityManagerFactory emf;

		@Override
		public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
			Set<EntityType<?>> entities = emf.getMetamodel().getEntities();
			config.exposeIdsFor(entities.stream().map(e -> e.getJavaType()).toArray(size -> new Class<?>[size]));
		}
	}
	
	@Bean
	@ConditionalOnMissingBean(PasswordEncoder.class)
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
