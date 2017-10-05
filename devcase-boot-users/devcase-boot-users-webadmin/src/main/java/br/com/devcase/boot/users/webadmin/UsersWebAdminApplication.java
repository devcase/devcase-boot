package br.com.devcase.boot.users.webadmin;

import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import br.com.devcase.boot.crud.jpa.repository.support.CriteriaJpaRepository;
import br.com.devcase.boot.users.security.config.EnableWebFormAuthentication;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass=CriteriaJpaRepository.class)
@PropertySource("classpath:/br/com/devcase/boot/users/webadmin/webadmin.properties")
@EnableWebFormAuthentication
public class UsersWebAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersWebAdminApplication.class, args);
	}

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
}
