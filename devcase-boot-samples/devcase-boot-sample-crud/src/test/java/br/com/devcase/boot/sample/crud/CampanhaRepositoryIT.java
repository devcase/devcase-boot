package br.com.devcase.boot.sample.crud;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.devcase.boot.sample.crud.config.SampleCrudConfig;
import br.com.devcase.boot.sample.crud.entity.Campanha;
import br.com.devcase.boot.sample.crud.repository.CampanhaRepository;


@RunWith(SpringRunner.class)
@SpringBootTest()
@DirtiesContext
@ContextConfiguration(classes=SampleCrudConfig.class)
@ActiveProfiles("integration-test")
public class CampanhaRepositoryIT {
	@Autowired
	private CampanhaRepository campanhaRepository;
	
	@Test
	public void testSalvar() throws Exception {
		Campanha c = new Campanha();
		c.setName("Campanha X");
		
		c = campanhaRepository.save(c);
		Assert.assertNotNull(c);
		Assert.assertNotNull(c.getId());
		
		Campanha c2 = campanhaRepository.findOne(c.getId());
		Assert.assertNotNull(c2);
		Assert.assertEquals(c.getId(), c2.getId());
	}
}
