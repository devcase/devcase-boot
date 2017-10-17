package br.com.devcase.boot.users.webadmin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.devcase.boot.users.domain.entities.PasswordCredential;
import br.com.devcase.boot.users.domain.entities.User;
import br.com.devcase.boot.users.repositories.CredentialRepository;
import br.com.devcase.boot.users.repositories.UserPermissionRepository;
import br.com.devcase.boot.users.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = UsersWebAdminApplication.class)
@EnableAutoConfiguration
@ActiveProfiles({"test", "test-h2"})
public class WebAdminRepositoriesTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CredentialRepository credentialRepository;
	@Autowired
	private UserPermissionRepository userPermissionRepository;

	@Before
	public void cleanDatabase() {
		userPermissionRepository.deleteAll();
		credentialRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	public void testSaveUser() {
		User user1 = new User();
		user1.setName("hirata1");
		userRepository.save(user1);
	}
	
	@Test
	public void testSaveUserWithInvalidUsername() {
		User user1 = new User();
		user1.setName("hirat a");
		try {
			userRepository.save(user1);
			Assert.fail();
		} catch (Exception ex) {
		}
	}
	
	@Test
	public void testSavePassword() {
		User user1 = new User();
		user1.setName("hirata2");
		userRepository.save(user1);
		
		PasswordCredential credential = new PasswordCredential();
		credential.setUser(user1);
		credential.setPassword("zode0s9812m!@#c90");
		
		credentialRepository.save(credential);
		
		Assert.assertEquals("zode0s9812m!@#c90", credentialRepository.findPasswordByUser(user1));
	}
	
	@Test
	public void testSaveTwoPasswordsForASingleUser() {
		User user1 = new User();
		user1.setName("hirata3");
		userRepository.save(user1);
		
		PasswordCredential credential = new PasswordCredential();
		credential.setUser(user1);
		credential.setPassword("papapapapapa");
		
		credentialRepository.save(credential);

		PasswordCredential credential2 = new PasswordCredential();
		credential2.setUser(user1);
		credential2.setPassword("papapapapapa2");
		
		try {
			credentialRepository.save(credential2);
			Assert.fail();
		} catch (Exception ex) {
			
		}
	}
	
}
