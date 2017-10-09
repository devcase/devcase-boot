package br.com.devcase.boot.sample.simplest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@ContextConfiguration(classes=Application.class)
@ActiveProfiles("integration-test")
public class SimplestSampleIT {
	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void testApplicationProperties() throws Exception {
		
		Assert.assertNotEquals("@project.artifactId@", applicationContext.getEnvironment().getProperty("controleacesso.testefiltering"));
		Assert.assertEquals("devcase-boot-sample-simplest", applicationContext.getEnvironment().getProperty("controleacesso.testefiltering"));
		Assert.assertEquals("áéçdçáskj1'dfoapaêô", applicationContext.getEnvironment().getProperty("controleacesso.testeencoding"));
	}
}
