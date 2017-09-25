package br.com.devcase.boot.webcrud;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.devcase.boot.webcrud.autoconfigure.WebCrudAutoConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ContextConfiguration(classes = {WebCrudAutoConfiguration.class, JacksonAutoConfiguration.class})
@ActiveProfiles({"test"})
public class WebCrudConfigTest {
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void testLocalDateJsonSerializer() throws Exception {
		LocalDate localDate = LocalDate.of(2017, 12, 30);
		String v = objectMapper.writeValueAsString(localDate);
		Assert.assertEquals("\"2017-12-30\"", v);
	}
}
