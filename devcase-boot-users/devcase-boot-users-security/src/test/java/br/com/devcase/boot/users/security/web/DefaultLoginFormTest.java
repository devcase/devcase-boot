package br.com.devcase.boot.users.security.web;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import br.com.devcase.boot.users.security.config.WebFormAuthenticationConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = WebFormAuthenticationConfig.class)
@EnableAutoConfiguration
@EnableGlobalAuthentication
@ActiveProfiles({ "test", "test-h2" })
public class DefaultLoginFormTest {

	MockMvc mockMvc;
	@Autowired
	WebApplicationContext ctx;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	@Test
	public void testDefaultLoginForm() throws Exception {
		LocalHostUriTemplateHandler t = new LocalHostUriTemplateHandler(ctx.getEnvironment());
		
		try (WebClient webClient = new WebClient()) {
			webClient.getOptions().setJavaScriptEnabled(false);
			webClient.getOptions().setCssEnabled(false);
			
			HtmlPage page = webClient.getPage(t.expand("/login").toString());
			assertEquals("Login", page.getTitleText());
			assertEquals(1, page.getBody().getElementsByAttribute("input", "type", "hidden").size());
			assertNotEquals("@csrftoken@", page.getBody().getElementsByAttribute("input", "type", "hidden").get(0).getAttribute("value"));
			assertEquals("_csrf", page.getBody().getElementsByAttribute("input", "type", "hidden").get(0).getAttribute("name"));
		}

	}
}
