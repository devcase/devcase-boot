package br.com.devcase.boot.crud.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.devcase.boot.crud.repository.testdomain.Publisher;
import br.com.devcase.boot.crud.repository.testdomain.PublisherRepository;


@RunWith(SpringRunner.class)
@SpringBootTest()
@ContextConfiguration(classes = RepositoryTestConfig.class)
@ActiveProfiles({"test", "test-h2"})
public class CriteriaRepositoryTest {
	@Autowired
	private PublisherRepository publisherRepository;
	@Autowired
	private EntityManagerFactory emf;
	
	@After
	public void afterTest() {
		publisherRepository.deleteAll();
	}
	
	@Test
	public void testSavePublisher() throws Exception {
		Publisher publisher = Publisher.builder().withName("Editora Devcase").build();
		publisherRepository.save(publisher);
		
		Publisher p = publisherRepository.findOne(publisher.getId());
		Assert.assertNotNull(p);
		Assert.assertEquals("Editora Devcase", p.getName());
	}
	
	@Test
	public void testUpdateProperty() throws Exception {
		Publisher publisher = Publisher.builder().withName("Editora Devcase").build();
		publisherRepository.save(publisher);
		final Long id = publisher.getId();
		
		Publisher p = publisherRepository.findOne(id);
		Assert.assertNotNull(p);
		Assert.assertEquals("Editora Devcase", p.getName());
		
		publisherRepository.updateProperty(id, "name", "Editora Nova Devcase");
		p = publisherRepository.findOne(id);
		Assert.assertNotNull(p);
		Assert.assertEquals("Editora Nova Devcase", p.getName());
	}
	
	@Test
	public void testEnvers() throws Exception {
		Publisher publisher = Publisher.builder().withName("Editora Devcase").build();
		publisherRepository.save(publisher);
		final Long id = publisher.getId();

		AuditReader auditReader = AuditReaderFactory.get(emf.createEntityManager());
		Publisher v1 = auditReader.find(Publisher.class, id, 1);
		Assert.assertEquals("Editora Devcase", v1.getName());

		
		Publisher p = new Publisher();
		p.setId(id);
		p.setName("Editora Nova Devcase");
		publisherRepository.save(p);

		publisherRepository.updateProperty(id, "name", "Editora Novíssima Devcase");

		v1 = auditReader.find(Publisher.class, id, 1);
		Publisher v2 = auditReader.find(Publisher.class, id, 2);
		Publisher v3 = auditReader.find(Publisher.class, id, 3);
		Assert.assertEquals("Editora Devcase", v1.getName());
		Assert.assertEquals("Editora Nova Devcase", v2.getName());
		Assert.assertEquals("Editora Novíssima Devcase", v3.getName());

	}
}
