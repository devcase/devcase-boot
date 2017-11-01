package br.com.devcase.boot.users.webadmin;

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
@ContextConfiguration(classes = { UsersWebAdminApplication.class, TestDataSetup.class } )
@EnableAutoConfiguration
@ActiveProfiles({ "test", "test-h2" })
@DirtiesContext
public class WebAdminRepositoriesSecurityTest {

	@Autowired
	private WebApplicationContext context;
	
	protected MockMvc mockMvc;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity()).build();
	}

	@Test
	public void testNotAuthenticatedUserRepositoryAccess() throws Exception {
		mockMvc.perform(get("/api/users"))
			.andExpect(status().isForbidden());
	}

	@Test
	public void testAuthenticatedAdminRepositoryAccess() throws Exception {
		mockMvc.perform(get("/api/userdetails").with(user("root").roles("ADMIN_USERS")))
				.andDo(print()).andDo(log());

		mockMvc.perform(get("/api/users").with(user("root").roles("ADMIN_USERS")))
			.andDo(print())
			.andDo(log())
			.andExpect(status().isOk());
	}

	@Test
	public void testAuthenticatedGuestRepositoryAccess() throws Exception {
		mockMvc.perform(get("/api/users").with(user("guest").roles("USER")))
			.andExpect(status().isForbidden());
	}
}
