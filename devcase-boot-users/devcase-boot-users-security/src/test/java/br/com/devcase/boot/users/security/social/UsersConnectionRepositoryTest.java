package br.com.devcase.boot.users.security.social;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.devcase.boot.users.security.config.WebFormAuthenticationConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = WebFormAuthenticationConfig.class)
@EnableAutoConfiguration
@Import(value = SocialLoginConfiguration.class)
@ActiveProfiles({ "test", "test-facebook" })
public class UsersConnectionRepositoryTest {
	@Autowired(required = false)
	UsersConnectionRepository usersConnectionRepository;
	@Autowired(required = false)
	SocialWebAutoConfiguration socialWebAutoConfiguration;

	@Test
	public void test() {
		assertNotNull(usersConnectionRepository);
		assertNotNull(socialWebAutoConfiguration);
	}
}
