package br.com.devcase.boot.sample.crud;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.javamoney.moneta.Money;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

import br.com.devcase.boot.crud.repository.criteria.Criteria;
import br.com.devcase.boot.crud.repository.criteria.Operation;
import br.com.devcase.boot.sample.crud.config.SampleCrudConfig;
import br.com.devcase.boot.sample.crud.entity.Campanha;
import br.com.devcase.boot.sample.crud.repository.CampanhaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest()
@DirtiesContext
@ContextConfiguration(classes = SampleCrudConfig.class)
@EnableAutoConfiguration
@ActiveProfiles("integration-test")
public class CampanhaRepositoryIT {
	@Autowired
	private CampanhaRepository campanhaRepository;

	@Test
	public void testSalvar() throws Exception {
		Campanha c = new Campanha();
		c.setNome("Campanha X");

		try {
			c = campanhaRepository.save(c);
			Assert.assertNotNull(c);
			Assert.assertNotNull(c.getId());

			Campanha c2 = campanhaRepository.findById(c.getId()).get();
			Assert.assertEquals(c.getId(), c2.getId());
		} finally {
			campanhaRepository.delete(c);
		}
	}

	@Test
	@SuppressWarnings({"rawtypes"})
	public void testFindByFilter() throws Exception {
		final LocalDate today = LocalDate.now();
		for (int i = 0; i < 30; i++) {
			Campanha c = Campanha.builder().withInicio(today.plusDays(i)).withOrcamento(Money.of((long) i, "USD"))
					.withNome("Exemplo de campanha " + (i + 1)).build();
			campanhaRepository.save(c);
		}

		List<Criteria> sampleCriteria = Lists
				.newArrayList(new Criteria<String, String>("nome", Operation.EQ, "Exemplo de campanha 13", String.class));

		List<Campanha> results = campanhaRepository.findAll(sampleCriteria);

		assertEquals(1, results.size());
		assertEquals("Exemplo de campanha 13", results.get(0).getNome());
	}
}
