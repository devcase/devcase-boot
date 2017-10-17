package br.com.devcase.boot.users.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import br.com.devcase.boot.users.security.config.CommonSecurityConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = CommonSecurityConfig.class)
@EnableAutoConfiguration
@EnableGlobalAuthentication
@ActiveProfiles({ "test", "test-h2" })
@DirtiesContext()
public class RestRepositoryNotExposedByDefaultIT {

	@Autowired
	private WebApplicationContext applicationContext;

	@Test
	public void checkRepositoryRestMvcConfiguration() {
		try {
			applicationContext.getBean(RepositoryRestMvcAutoConfiguration.class);
			Assert.fail();
		} catch (NoSuchBeanDefinitionException ex) {
		}
	}
}
