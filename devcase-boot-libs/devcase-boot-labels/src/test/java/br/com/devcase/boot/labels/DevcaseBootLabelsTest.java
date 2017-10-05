package br.com.devcase.boot.labels;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=DevcaseBootLabelsTest.class)
@EnableDevcaseLabels
@EnableAutoConfiguration
public class DevcaseBootLabelsTest {
	@Autowired
	private MessageSource messageSource;

	@Test
	public void test() throws Exception {
		Assert.assertEquals("Próximo", messageSource.getMessage("action.next", new Object[0], new Locale("pt")));
	}
}
