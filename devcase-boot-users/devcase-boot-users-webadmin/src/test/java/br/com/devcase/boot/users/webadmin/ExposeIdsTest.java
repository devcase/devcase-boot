package br.com.devcase.boot.users.webadmin;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { UsersWebAdminApplication.class, TestDataSetup.class } )
@EnableAutoConfiguration
@ActiveProfiles({"test", "test-h2"})
public class ExposeIdsTest {
	@Autowired
	WebApplicationContext wac;

	@Test
	public void testExposeIdsPresent() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		mockMvc.perform(get("/api/users").with(user("root").roles("ADMIN_USERS")))
			 .andDo(print())
			 .andDo(log())
			 .andExpect(status().isOk())
			 .andExpect(jsonPath("$._embedded.users[*].id").exists());
	}
}
