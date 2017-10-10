package br.com.devcase.boot.users.webadmin;

import java.util.Date;
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
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Component;

import br.com.devcase.boot.crud.jpa.repository.support.CriteriaJpaRepository;
import br.com.devcase.boot.users.domain.entities.User;
import br.com.devcase.boot.users.security.social.EnableSocialLogin;
import br.com.devcase.boot.users.webadmin.repositories.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass=CriteriaJpaRepository.class)
@PropertySource("classpath:/br/com/devcase/boot/users/webadmin/webadmin.properties")
//@EnableWebFormAuthentication
@EnableSocialLogin
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
	
	@Component
	public static class SocialSignUp implements ConnectionSignUp 
	{
		@Autowired
		private UserRepository userRepository;
		@Autowired
		private UsersConnectionRepository usersConnectionRepository;

		@Override
		public String execute(Connection<?> connection) {
			UserProfile up = connection.fetchUserProfile();
			
			User user = userRepository.findByName(up.getEmail());
			ConnectionData cd = connection.createData();
			if(user == null) {
				user = new User();
				user.setName(up.getEmail());
				user.setEnabled(true);
				user.setLocked(false);
				user.setValidUntil(cd.getExpireTime() == null ? null : new Date(cd.getExpireTime()));
				userRepository.save(user);
			}
			
			usersConnectionRepository.createConnectionRepository(user.getName());
			return user.getName();
		}
		
	}
}
