package br.com.devcase.sample.webcrud;

import java.text.MessageFormat;
import java.time.LocalDate;

import org.javamoney.moneta.Money;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.devcase.boot.sample.crud.entity.Campanha;
import br.com.devcase.boot.sample.crud.repository.CampanhaRepository;
import br.com.devcase.boot.sample.webcrud.SampleWebCrudApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SampleWebCrudApplication.class)
@ActiveProfiles("test")
public class WebCrudListTest {

	@Autowired
	private CampanhaRepository campanhaRepository;
	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setup() {
		LocalDate firstDate = LocalDate.of(2017, 9, 17);
		for (int i = 0; i < 30; i++) {
			Campanha c = Campanha.builder().withInicio(firstDate.minusDays(i / 2))
					.withOrcamento(Money.of((long) 10000 - i, "USD"))
					.withNome(MessageFormat.format("WebCrudListTest {0,number,00}", i + 1)).build();
			campanhaRepository.save(c);
		}
	}

	@After
	public void cleanup() {
		campanhaRepository.delete(campanhaRepository.findByNomeStartingWith("WebCrudListTest"));
	}

	@Test
	public void testList() {
		String body = this.restTemplate.getForObject("/campanha", String.class);
		Assert.assertTrue(body.contains("WebCrudListTest 01"));
		Assert.assertTrue(body.contains("WebCrudListTest 02"));
		Assert.assertTrue(body.contains("WebCrudListTest 20"));
		Assert.assertFalse(body.contains("WebCrudListTest 21"));
		Assert.assertFalse(body.contains("WebCrudListTest 27"));
	}

	@Test
	public void testListWithFilter() {
		ResponseEntity<String> response = this.restTemplate.getForEntity("/campanha?nome=WebCrudListTest 27",
				String.class);
		String body = response.getBody();
		Assert.assertFalse(body.contains("WebCrudListTest 01"));
		Assert.assertFalse(body.contains("WebCrudListTest 02"));
		Assert.assertFalse(body.contains("WebCrudListTest 20"));
		Assert.assertFalse(body.contains("WebCrudListTest 21"));
		Assert.assertTrue(body.contains("WebCrudListTest 27"));
	}

	@Test
	public void testListWithDateFilter() {
		ResponseEntity<String> response = this.restTemplate.getForEntity("/campanha?inicio=2017-09-16", String.class);
		String body = response.getBody();
		Assert.assertFalse(body.contains("WebCrudListTest 01"));
		Assert.assertFalse(body.contains("WebCrudListTest 02"));
		Assert.assertTrue(body.contains("WebCrudListTest 03"));
		Assert.assertTrue(body.contains("WebCrudListTest 04"));
		Assert.assertFalse(body.contains("WebCrudListTest 05"));
		Assert.assertFalse(body.contains("WebCrudListTest 20"));
		Assert.assertFalse(body.contains("WebCrudListTest 21"));
		Assert.assertFalse(body.contains("WebCrudListTest 27"));
	}

	@Test
	public void testSorting() {
		ResponseEntity<String> response = this.restTemplate.getForEntity("/campanha?sort=nome,desc", String.class);
		String body = response.getBody();
		Assert.assertTrue(body.contains("WebCrudListTest 30"));
		Assert.assertTrue(body.contains("WebCrudListTest 29"));
		Assert.assertTrue(body.contains("WebCrudListTest 28"));
		Assert.assertFalse(body.contains("WebCrudListTest 09"));
		Assert.assertFalse(body.contains("WebCrudListTest 01"));
	}

	@Test
	public void testSorting2() {
		ResponseEntity<String> response = this.restTemplate.getForEntity("/campanha?sort=inicio", String.class);
		String body = response.getBody();
		Assert.assertTrue(body.contains("WebCrudListTest 30"));
		Assert.assertTrue(body.contains("WebCrudListTest 29"));
		Assert.assertTrue(body.contains("WebCrudListTest 28"));
		Assert.assertFalse(body.contains("WebCrudListTest 09"));
		Assert.assertFalse(body.contains("WebCrudListTest 01"));
	}
}
