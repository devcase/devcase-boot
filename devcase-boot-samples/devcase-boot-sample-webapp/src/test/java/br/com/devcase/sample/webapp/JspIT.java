package br.com.devcase.sample.webapp;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.devcase.boot.sample.webapp.WebApplication;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes=WebApplication.class)
@ActiveProfiles("integration-test")
public class JspIT {


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
	@Test
	public void testSample3Jsp() throws Exception {
		String body = this.restTemplate.getForObject("/sample3.jsp", String.class);
		Assert.assertTrue(body.contains("Sample: Hello"));
	}


	@Test
	public void testSample4Jsp() throws Exception {
		String body = this.restTemplate.getForObject("/sample4.jsp", String.class);
		Assert.assertTrue(body.contains("Example: Carregado com sucesso!"));
	}
}
