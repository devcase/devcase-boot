package br.com.devcase.sample.sitemesh;

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

import br.com.devcase.boot.sample.sitemesh.SiteMeshSampleApplication;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes=SiteMeshSampleApplication.class)
@ActiveProfiles("integration-test")
public class SiteMeshIT {


	@Autowired
	private TestRestTemplate restTemplate;	

	
	@Test
	public void testIndex() throws Exception {
		String body = this.restTemplate.getForObject("/", String.class);
		Assert.assertTrue("Não encontrou conteúdo de index.jsp", body.contains("Body Title"));
		Assert.assertTrue("Não encontrou conteúdo do decorador", body.contains("This text is in the decorator"));
	}
}