package br.com.devcase.boot.users.domain;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { RestRepositoryConfig.class } )
@ActiveProfiles({ "test", "test-h2" })
@DirtiesContext
public class RepositoryRestAccessTest {
	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;
	
	@Before
	public void instantiateMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.build();
	}

	@Test
	public void testCredentialRepositoryShouldntBeExported() throws Exception {
		mockMvc.perform(get("/api/profile"))
			.andDo(log())
			.andDo(print())
			.andExpect(jsonPath("$._links").exists())
			.andExpect(jsonPath("$._links.users").exists())
			.andExpect(jsonPath("$._links.credentials").doesNotExist())
			
			;
	}
}
