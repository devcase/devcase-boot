package br.com.devcase.boot.users.webadmin;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.devcase.boot.users.domain.entities.PasswordCredential;
import br.com.devcase.boot.users.domain.entities.User;
import br.com.devcase.boot.users.domain.entities.UserPermission;
import br.com.devcase.boot.users.domain.entities.oauth2.OAuth2Client;

@Configuration
public class TestDataSetup {

	
	@Autowired
	private EntityManager em;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void loadRootUser() {
		final String password = "root";
		final String login = "root";
		if(em.createQuery("select e from User e where e.username = :username").setParameter("username", login).getResultList().isEmpty()) {
			User user1 = new User();
			user1.setUsername(login);
			em.persist(user1);
			PasswordCredential credential = new PasswordCredential();
			credential.setUser(user1);
			credential.setPassword(passwordEncoder.encode(password));
			em.persist(credential);
			em.persist(UserPermission.builder().withRole("USER").withUser(user1).build());
			em.persist(UserPermission.builder().withRole("ACTUATOR").withUser(user1).build());
			em.persist(UserPermission.builder().withRole("ADMIN_USERS").withUser(user1).build());
		}
	}
	
	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void loadGuestUser() {
		final String password = "guest";
		final String login = "guest";
		if(em.createQuery("select e from User e where e.username = :username").setParameter("username", login).getResultList().isEmpty()) {
			User user1 = new User();
			user1.setUsername(login);
//			user1.setRoles(Lists.newArrayList("ROLE_USER"));
			em.persist(user1);
			PasswordCredential credential = new PasswordCredential();
			credential.setUser(user1);
			credential.setPassword(passwordEncoder.encode(password));
			em.persist(credential);
			em.persist(UserPermission.builder().withRole("USER").withUser(user1).build());
		}
	}
	
	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void loadDevClient() {
		final String login = "ASKdBkpwYwtZhsur1HEUP9QJK2wg6FqC";
		final String password = "E4KLddpituzFF6vDqA2ohHFRLJgTRfxx";
		if(em.createQuery("select e from OAuth2Client e where e.clientId = :clientId").setParameter("clientId", login).getResultList().isEmpty()) {
			em.persist(
					OAuth2Client.builder()
					.withClientSecret(password)
					.withAccessTokenValidity(3600)
					.withRefreshTokenValidity(3600)
					.withName("DEV")
					.withAuthorizedGrantTypes("authorization_code,refresh_token")
					.withScope("email,profile")
					.withClientId(login).build());
		}
	}

}
