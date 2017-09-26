package br.com.devcase.boot.users.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.devcase.boot.users.domain.entities.PasswordCredential;
import br.com.devcase.boot.users.domain.entities.User;
import br.com.devcase.boot.users.domain.repositories.CredentialRepository;
import br.com.devcase.boot.users.domain.repositories.UserRepository;
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
	private UserRepository userRepository;
	@Autowired
	private CredentialRepository credentialRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void testAuthentication() throws Exception {
		final String password = "BatataUnicórnio1980";
		final String login = "integrationtest1";
		User user1 = new User();
		user1.setName(login);
		userRepository.save(user1);

		PasswordCredential credential = new PasswordCredential();
		credential.setUser(user1);
		credential.setPassword(passwordEncoder.encode(password));
		credentialRepository.save(credential);
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, "DesertoEstacionamento"));
			Assert.fail();
		} catch (AuthenticationException ex) {
		}
	}

}
