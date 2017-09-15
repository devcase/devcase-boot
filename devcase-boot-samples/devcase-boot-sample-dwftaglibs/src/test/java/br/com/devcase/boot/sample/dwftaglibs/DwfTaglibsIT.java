package br.com.devcase.boot.sample.dwftaglibs;


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


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes=DwftaglibsSampleApplication.class)
@ActiveProfiles("integration-test")
public class DwfTaglibsIT {


	@Autowired
	private TestRestTemplate restTemplate;	

	
	@Test
	public void testResolveEL() throws Exception {
		String body = this.restTemplate.getForObject("/resolve-el.jsp", String.class);
		Assert.assertTrue(body.contains("Using resolveEL tag: texto"));
	}
	
}