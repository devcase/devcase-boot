package br.com.devcase.boot.users.security;

import java.time.Instant;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.devcase.boot.users.domain.entities.PasswordCredential;
import br.com.devcase.boot.users.domain.entities.User;
import br.com.devcase.boot.users.domain.entities.UserPermission;
import br.com.devcase.boot.users.security.config.CommonSecurityConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
@ContextConfiguration(classes = CommonSecurityConfig.class)
@EnableAutoConfiguration
@EnableGlobalAuthentication
@ActiveProfiles({ "test", "test-h2" })
@DirtiesContext()
public class DefaultUserDetailsServiceManagerTest {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private EntityManagerFactory emf;
	@Autowired
	private PasswordEncoder passwordEncoder;

	final String password = "BatataUnicórnio1980";
	final String validUserLogin = "integrationtest1";
	final String expiredUserLogin = "integrationtest2";
	final String validRoleName = "USER";
	final String validTemporaryRoleName = "TEMPORARY";
	final String expiredRoleName = "ADMIN";

	
	@Before
	public void setup() throws Exception {
		
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			em.createQuery("delete from UserPermission c").executeUpdate();
			em.createQuery("delete from Credential c").executeUpdate();
			em.createQuery("delete from User c").executeUpdate();
			tx.commit();
		} finally {
			em.close();
		}

		try {
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			User user1 = new User();
			user1.setName(validUserLogin);
			em.persist(user1);

			User user2 = new User();
			user2.setName(expiredUserLogin);
			user2.setValidUntil(Date.from(Instant.now().minusSeconds(60 * 60 * 24)));
			em.persist(user2);

			PasswordCredential credential = new PasswordCredential();
			credential.setUser(user1);
			credential.setPassword(passwordEncoder.encode(password));
			em.persist(credential);

			PasswordCredential credential2 = new PasswordCredential();
			credential2.setUser(user2);
			credential2.setPassword(passwordEncoder.encode(password));
			em.persist(credential2);

			UserPermission validRole = new UserPermission();
			validRole.setUser(user1);
			validRole.setRole(validRoleName);
			em.persist(validRole);

			UserPermission expiredRole = new UserPermission();
			expiredRole.setUser(user1);
			expiredRole.setRole(expiredRoleName);
			expiredRole.setValidUntil(Date.from(Instant.now().minusSeconds(60 * 60 * 24)));
			em.persist(expiredRole);

			UserPermission validTemporaryRole = new UserPermission();
			validTemporaryRole.setUser(user1);
			validTemporaryRole.setRole(validTemporaryRoleName);
			validTemporaryRole.setValidUntil(Date.from(Instant.now().plusSeconds(60 * 60 * 24)));
			em.persist(validTemporaryRole);

			em.flush();
			tx.commit();
		} finally {
			em.close();
		}

	}

	@Test
	public void testAuthentication() throws Exception {
		
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(validUserLogin, password));

		Assert.assertNotNull(authentication);
		Assert.assertTrue(authentication.isAuthenticated());
		Assert.assertTrue(authentication.getAuthorities().stream()
				.filter(it -> it.getAuthority().equals("ROLE_" + validRoleName)).count() > 0);
		Assert.assertTrue(authentication.getAuthorities().stream()
				.filter(it -> it.getAuthority().equals("ROLE_" + validTemporaryRoleName)).count() > 0);
		Assert.assertTrue(authentication.getAuthorities().stream()
				.filter(it -> it.getAuthority().equals("ROLE_" + expiredRoleName)).count() == 0);

		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(validUserLogin, "DesertoEstacionamento"));
			Assert.fail();
		} catch (AuthenticationException ex) {
		}
	}

	@Test
	public void testAuthenticationExpiredUser() throws Exception {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(expiredUserLogin, password));
			Assert.fail();
		} catch (AuthenticationException ex) {

		}
	}

}
