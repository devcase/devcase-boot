package br.com.devcase.boot.sample.simplest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.devcase.boot.sample.simplest.Application;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@ContextConfiguration(classes=Application.class)
public class ApplicationIT {
	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void testApplicationProperties() throws Exception {
		Assert.assertEquals("áéçdçáskj1'dfoapaêô", applicationContext.getEnvironment().getProperty("controleacesso.testeencoding"));
		Assert.assertNotEquals("@project.version@", applicationContext.getEnvironment().getProperty("controleacesso.testefiltering"));
	}
}
