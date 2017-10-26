package br.com.devcase.boot.users.webadmin;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.devcase.boot.webcrud.autoconfigure.WebCrudAutoConfiguration.ExposeIds;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = UsersWebAdminApplication.class)
@EnableAutoConfiguration
@ActiveProfiles({"test", "test-h2"})
public class ExposeIdsTest {
	
	@Autowired(required=false)
	ExposeIds exposeIds;
	
	@Test
	public void testExposeIdsPresent() {
		Assert.assertNotNull(exposeIds);
	}
}
