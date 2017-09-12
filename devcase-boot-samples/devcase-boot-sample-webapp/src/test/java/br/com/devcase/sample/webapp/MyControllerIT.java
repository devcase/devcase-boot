package br.com.devcase.sample.webapp;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.devcase.boot.sample.webapp.WebApplication;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@DirtiesContext
@ContextConfiguration(classes=WebApplication.class)
@ActiveProfiles("integration-test")
public class MyControllerIT {

	@Autowired
	private TestRestTemplate restTemplate;	

	
	@Test
	public void testMyControllerIndex() throws Exception {
		String body = this.restTemplate.getForObject("/my", String.class);
		Assert.assertTrue(body.contains("TEXT TEXT TEXT"));
	}
}
