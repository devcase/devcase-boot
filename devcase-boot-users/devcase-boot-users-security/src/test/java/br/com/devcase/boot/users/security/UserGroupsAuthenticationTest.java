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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

import br.com.devcase.boot.users.domain.entities.GroupPermission;
import br.com.devcase.boot.users.domain.entities.PasswordCredential;
import br.com.devcase.boot.users.domain.entities.User;
import br.com.devcase.boot.users.domain.entities.UserGroup;
import br.com.devcase.boot.users.security.config.CommonSecurityConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
@ContextConfiguration(classes = CommonSecurityConfig.class)
@EnableAutoConfiguration
@EnableGlobalAuthentication
@ActiveProfiles({ "test", "test-h2" })
@DirtiesContext()
public class UserGroupsAuthenticationTest {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private EntityManagerFactory emf;
	@Autowired
	private PasswordEncoder passwordEncoder;

	final String password = "BebêUnicórnio@";
	final String validUserLogin = "integrationtest7";
	final String groupName = "Administradores";
	final String otherGroupName = "Vendedores";
	final String validRoleName = "USER";
	final String validTemporaryRoleName = "TEMPORARY";
	final String expiredRoleName = "ADMIN";
	final String otherGroupRoleName = "SELL";

	@Before
	public void setup() throws Exception {
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			em.createQuery("delete from GroupPermission").executeUpdate();
			em.createQuery("delete from UserPermission").executeUpdate();
			em.createQuery("delete from Credential").executeUpdate();
			em.createQuery("delete from User").executeUpdate();
			em.createQuery("delete from UserGroup").executeUpdate();
			
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
			user1.setGroups(Lists.newArrayList());
			em.persist(user1);

			PasswordCredential credential = new PasswordCredential();
			credential.setUser(user1);
			credential.setPassword(passwordEncoder.encode(password));
			em.persist(credential);

			UserGroup userGroup1 = new UserGroup();
			userGroup1.setName(groupName);
			em.persist(userGroup1);
			
			user1.getGroups().add(userGroup1);
			
			GroupPermission gp1 = GroupPermission.builder().withGroup(userGroup1).withRole(validRoleName).build();
			em.persist(gp1);
			
			GroupPermission gp2 = GroupPermission.builder().withGroup(userGroup1).withRole(expiredRoleName)
					.withValidUntil(Date.from(Instant.now().minusSeconds(60 * 60 * 24))).build();
			em.persist(gp2);
			
			GroupPermission gp3 = GroupPermission.builder().withGroup(userGroup1).withRole(validTemporaryRoleName)
					.withValidUntil(Date.from(Instant.now().plusSeconds(60 * 60 * 24))).build();
			em.persist(gp3);
			
			UserGroup otherGroup = new UserGroup();
			otherGroup.setName(otherGroupName);
			em.persist(otherGroup);
			
			GroupPermission otherPermission = GroupPermission.builder().withGroup(otherGroup).withRole(otherGroupRoleName).build();
			em.persist(otherPermission);

			em.flush();
			tx.commit();
		} finally {
			em.close();
		}

	}

	@Test
	public void testGroupsPermissions() throws Exception {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(validUserLogin, password));

		Assert.assertNotNull(authentication);
		Assert.assertTrue(authentication.isAuthenticated());
		Assert.assertEquals(2, authentication.getAuthorities().size());
		Assert.assertTrue(authentication.getAuthorities().stream()
				.filter(it -> it.getAuthority().equals("ROLE_" + validRoleName)).count() > 0);
		Assert.assertTrue(authentication.getAuthorities().stream()
				.filter(it -> it.getAuthority().equals("ROLE_" + validTemporaryRoleName)).count() > 0);
		Assert.assertTrue(authentication.getAuthorities().stream()
				.filter(it -> it.getAuthority().equals("ROLE_" + expiredRoleName)).count() == 0);
		Assert.assertTrue(authentication.getAuthorities().stream()
				.filter(it -> it.getAuthority().equals("ROLE_" + otherGroupRoleName)).count() == 0);

	}
}
