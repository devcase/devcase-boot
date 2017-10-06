package br.com.devcase.boot.sample.crud;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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

import br.com.devcase.boot.sample.crud.config.SampleCrudConfig;
import br.com.devcase.boot.sample.crud.entity.Anuncio;
import br.com.devcase.boot.sample.crud.entity.Campanha;
import br.com.devcase.boot.sample.crud.repository.AnuncioRepository;
import br.com.devcase.boot.sample.crud.repository.CampanhaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest()
@DirtiesContext
@ContextConfiguration(classes = SampleCrudConfig.class)
@EnableAutoConfiguration
@ActiveProfiles("integration-test")
public class AnuncioRepositoryTest {
	@Autowired
	private AnuncioRepository anuncioRepository;
	@Autowired
	private CampanhaRepository campanhaRepository;

	@Test
	public void testSalvarAnuncio() throws Exception {
		Campanha campanha = Campanha.builder().withInicio(LocalDate.of(2017, 9, 17)).withNome("Lançamento do produto X")
				.build();

		campanhaRepository.save(campanha);

		Anuncio anuncio1 = Anuncio.builder().withCampanha(campanha)
				.withInicio(ZonedDateTime.of(LocalDate.of(2017, 9, 17).atStartOfDay(), ZoneId.of("America/Sao_Paulo")))
				.withTitulo("você já conheceu o produto X?").build();
		anuncioRepository.save(anuncio1);
		
		Assert.assertEquals(campanha.getId(), anuncioRepository.findById(anuncio1.getId()).get().getCampanha().getId());
		
		Assert.assertTrue(anuncioRepository.findByCampanha(campanha).stream().filter(a -> a.getId().equals(anuncio1.getId())).count() == 1);
	}
}
