package br.com.devcase.boot.users.security;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.TransactionManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
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

import com.google.common.collect.Lists;

import br.com.devcase.boot.users.domain.entities.PasswordCredential;
import br.com.devcase.boot.users.domain.entities.User;
import br.com.devcase.boot.users.security.autoconfigure.DevcaseUsersSecurityAutoConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ContextConfiguration(classes = DevcaseUsersSecurityAutoConfiguration.class)
@EnableAutoConfiguration
@EnableGlobalAuthentication
@ActiveProfiles({ "test", "test-h2" })
@DirtiesContext()
public class DefaultUserServiceManagerTest {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private EntityManagerFactory emf;
	@Autowired
	private PasswordEncoder passwordEncoder;

	final String password = "BatataUnicórnio1980";
	final String login = "integrationtest1";

	@Before
	public void setup() throws Exception {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			
			User user1 = new User();
			user1.setName(login);
			user1.setRoles(Lists.newArrayList("ROLE_USER", "ROLE_ADMIN"));
			em.persist(user1);
	
			PasswordCredential credential = new PasswordCredential();
			credential.setUser(user1);
			credential.setPassword(passwordEncoder.encode(password));
			em.persist(credential);

			em.flush();
			tx.commit();
		} finally {
			em.close();
		}

	}
	
	@Test
	public void testAuthentication() throws Exception {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));

		Assert.assertNotNull(authentication);
		Assert.assertTrue(authentication.isAuthenticated());
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, "DesertoEstacionamento"));
			Assert.fail();
		} catch (AuthenticationException ex) {
		}
	}

}
