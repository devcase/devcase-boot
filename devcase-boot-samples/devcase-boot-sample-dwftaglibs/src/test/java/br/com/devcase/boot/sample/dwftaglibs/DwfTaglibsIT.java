package br.com.devcase.boot.sample.dwftaglibs;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = DwftaglibsSampleApplication.class)
@ActiveProfiles("integration-test")
public class DwfTaglibsIT {
	@Autowired
	WebApplicationContext context;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testResolveEL() throws Exception {
		String body = this.restTemplate.getForObject("/resolve-el.jsp", String.class);
		Assert.assertTrue(body.contains("Using resolveEL tag: texto"));
	}

	@Test
	public void testAutoFormatDate() throws Exception {
		String body = this.restTemplate.getForObject("/auto-format-date.jsp", String.class);
		Assert.assertTrue(body.contains("Date (pt-BR, default timezone): 31/12/2017"));
		Assert.assertTrue(body.contains("Date (en-US, default timezone): Dec 31, 2017"));
	}

	@Test
	public void testAddJavascript() throws Exception {
//		String body = restTemplate.getForObject("/add-javascript.jsp", String.class);
//		ChromeDriver driver = new ChromeDriver();
//		driver.
		LocalHostUriTemplateHandler t = new LocalHostUriTemplateHandler(context.getEnvironment());
		
		HtmlUnitDriver driver = new HtmlUnitDriver(true);
		driver.get(t.expand("/add-javascript.jsp").toString());
		Assert.assertEquals("Add Javascript", driver.getTitle());
		List<WebElement> scriptElements = driver.findElementsByTagName("script");
		Assert.assertEquals(5, scriptElements.size());
		Assert.assertTrue(scriptElements.get(0).getAttribute("src").contains("jquery"));
		Assert.assertTrue(scriptElements.get(1).getAttribute("src").contains("popper"));
		Assert.assertTrue(scriptElements.get(2).getAttribute("src").contains("bootstrap"));
		Assert.assertTrue(scriptElements.get(3).getAttribute("src").contains("angular"));
		Assert.assertNull(scriptElements.get(4).getAttribute("src"));
		//driver.executeScript("$('h1').text(\"Altered by javascript2\");");
		Assert.assertEquals("altered by javascript on document ready", driver.findElementByTagName("h1").getText());
	}
}