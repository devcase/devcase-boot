package br.com.devcase.sample.webapp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.devcase.boot.sample.webapp.WebApplication;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@DirtiesContext
@ContextConfiguration(classes=WebApplication.class)
public class JspIT {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private TestRestTemplate restTemplate;	

	
	@Test
	public void testSampleJsp() throws Exception {
		String body = this.restTemplate.getForObject("/sample.jsp", String.class);
		Assert.assertTrue(body.contains("OK"));
	}
	
	@Test
	public void testSample2Jsp() throws Exception {
		String body = this.restTemplate.getForObject("/sample2.jsp", String.class);
		Assert.assertTrue(body.contains("1300"));
	}
}
