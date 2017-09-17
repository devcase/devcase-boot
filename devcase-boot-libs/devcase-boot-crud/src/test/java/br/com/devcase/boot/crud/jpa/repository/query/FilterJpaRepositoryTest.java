package br.com.devcase.boot.crud.jpa.repository.query;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import com.google.common.collect.Lists;

import br.com.devcase.boot.crud.jpa.repository.support.FilterJpaRepository;

@RunWith(MockitoJUnitRunner.class)
public class FilterJpaRepositoryTest {

	public static class ExampleDomainClass {
//		private String id;
//		private String name;
//		private Long weight;
//		private LocalDate dayOfBirth;
//		private Date creationDate;
//
//		public String getId() {
//			return id;
//		}
//
//		public void setId(String id) {
//			this.id = id;
//		}
//
//		public String getName() {
//			return name;
//		}
//
//		public void setName(String name) {
//			this.name = name;
//		}
//
//		public Long getWeight() {
//			return weight;
//		}
//
//		public void setWeight(Long weight) {
//			this.weight = weight;
//		}
//
//		public LocalDate getDayOfBirth() {
//			return dayOfBirth;
//		}
//
//		public void setDayOfBirth(LocalDate dayOfBirth) {
//			this.dayOfBirth = dayOfBirth;
//		}
//		
//		
	}

	@Mock EntityManager em;
	@Mock EntityManagerFactory emf;
	@Mock CriteriaBuilder builder;
	@Mock CriteriaQuery<ExampleDomainClass> criteriaQuery;
	@Mock JpaEntityInformation<ExampleDomainClass, String> information;
	@Mock TypedQuery<ExampleDomainClass> typedQuery;
	@Mock javax.persistence.Query query;
	@Mock Metamodel metamodel;

	FilterJpaRepository<ExampleDomainClass, String> filterJpaRepository;

	@Before
	public void setUp() {
		when(information.getJavaType()).thenReturn(ExampleDomainClass.class);

		when(em.getMetamodel()).thenReturn(metamodel);
		when(em.getDelegate()).thenReturn(em);
		when(em.getEntityManagerFactory()).thenReturn(emf);
		when(emf.createEntityManager()).thenReturn(em);
		when(em.createQuery(anyString())).thenReturn(query);
		
		filterJpaRepository = spy(new FilterJpaRepository<>(information, em));
	}
	
	@Test
	public void testFilterJpaRepository() {
		List<Criteria<?>> sampleCriteria = Lists.newArrayList(
					new Criteria<String>("name", Operation.EQ, "Fulano de tal", String.class),
					new Criteria<Long>("weight", Operation.GT, 14L, Long.class)
				);
		
		CriteriaSource mockCriteria = mock(CriteriaSource.class);
		when(mockCriteria.getCriteriaFor(any())).thenReturn(sampleCriteria);
		
		
		filterJpaRepository.findAll(mockCriteria);
		
		ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
		verify(em).createQuery(queryCaptor.capture());
		
		Assert.assertTrue(queryCaptor.getValue().contains("name = ?1"));
		Assert.assertTrue(queryCaptor.getValue().contains("weight > ?2"));
		
		verify(query).setParameter(1, "Fulano de tal");
		verify(query).setParameter(2, 14L);
	}
	
	@Test
	public void testStringOperations() {
		List<Criteria<?>> sampleCriteria = Lists.newArrayList(
					new Criteria<String>("name", Operation.EQ, "Fulano de tal", String.class),
					new Criteria<String>("name", Operation.NE, "Silvia Gomes", String.class),
					new Criteria<String>("name", Operation.ISNULL, null, String.class)
				);
		
		CriteriaSource mockCriteria = mock(CriteriaSource.class);
		when(mockCriteria.getCriteriaFor(any())).thenReturn(sampleCriteria);
		
		
		filterJpaRepository.findAll(mockCriteria);
		
		ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
		verify(em).createQuery(queryCaptor.capture());
		
		String createdQuery = queryCaptor.getValue();
		
		Assert.assertTrue(createdQuery.contains("name = ?1"));
		Assert.assertTrue(createdQuery.contains("name <> ?2"));
		Assert.assertTrue(createdQuery.contains("name is null"));
	}
	
	@Test
	public void testNumericOperations() {
		List<Criteria<?>> sampleCriteria = Lists.newArrayList(
				new Criteria<Long>("weight", Operation.EQ, 1L, Long.class),
				new Criteria<Long>("weight", Operation.NE, 2L, Long.class),
				new Criteria<Long>("weight", Operation.ISNULL, null, Long.class),
				new Criteria<Long>("weight", Operation.GT, 3L, Long.class),
				new Criteria<Long>("weight", Operation.LT, 4L, Long.class),
				new Criteria<Long>("weight", Operation.GTE, 5L, Long.class),
				new Criteria<Long>("weight", Operation.LTE, 6L, Long.class)
			);
		CriteriaSource mockCriteria = mock(CriteriaSource.class);
		when(mockCriteria.getCriteriaFor(any())).thenReturn(sampleCriteria);
		
		
		filterJpaRepository.findAll(mockCriteria);
		
		ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
		verify(em).createQuery(queryCaptor.capture());
		
		String createdQuery = queryCaptor.getValue();
		
		Assert.assertTrue(createdQuery.contains("weight = ?1"));
		Assert.assertTrue(createdQuery.contains("weight <> ?2"));
		Assert.assertTrue(createdQuery.contains("weight is null"));
		Assert.assertTrue(createdQuery.contains("weight > ?3"));
		Assert.assertTrue(createdQuery.contains("weight < ?4"));
		Assert.assertTrue(createdQuery.contains("weight >= ?5"));
		Assert.assertTrue(createdQuery.contains("weight <= ?6"));
	}

	@Test
	public void testLocalDateOperations() {
		LocalDate ld = LocalDate.of(2017, 9, 16);
		List<Criteria<?>> sampleCriteria = Lists.newArrayList(
					new Criteria<LocalDate>("dayOfBirth", Operation.EQ, ld.plusDays(1), LocalDate.class),
					new Criteria<LocalDate>("dayOfBirth", Operation.NE, ld.plusDays(2), LocalDate.class),
					new Criteria<LocalDate>("dayOfBirth", Operation.ISNULL, null, LocalDate.class),
					new Criteria<LocalDate>("dayOfBirth", Operation.GT, ld.plusDays(3), LocalDate.class),
					new Criteria<LocalDate>("dayOfBirth", Operation.LT, ld.plusDays(4), LocalDate.class),
					new Criteria<LocalDate>("dayOfBirth", Operation.GTE, ld.plusDays(5), LocalDate.class),
					new Criteria<LocalDate>("dayOfBirth", Operation.LTE, ld.plusDays(6), LocalDate.class)
				);
		
		CriteriaSource mockCriteria = mock(CriteriaSource.class);
		when(mockCriteria.getCriteriaFor(any())).thenReturn(sampleCriteria);
		
		
		filterJpaRepository.findAll(mockCriteria);
		
		ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
		verify(em).createQuery(queryCaptor.capture());
		
		String createdQuery = queryCaptor.getValue();
		
		Assert.assertTrue(createdQuery.contains("dayOfBirth = ?1"));
		Assert.assertTrue(createdQuery.contains("dayOfBirth <> ?2"));
		Assert.assertTrue(createdQuery.contains("dayOfBirth is null"));
		Assert.assertTrue(createdQuery.contains("dayOfBirth > ?3"));
		Assert.assertTrue(createdQuery.contains("dayOfBirth < ?4"));
		Assert.assertTrue(createdQuery.contains("dayOfBirth >= ?5"));
		Assert.assertTrue(createdQuery.contains("dayOfBirth <= ?6"));
	}

	@Test
	public void testDateOperations() {
		ZonedDateTime ld = LocalDate.of(2017, 9, 16).atStartOfDay(ZoneId.of("America/Sao_Paulo"));
		Date d = new Date();
		List<Criteria<?>> sampleCriteria = Lists.newArrayList(
					new Criteria<Date>("dayOfBirth", Operation.EQ, Date.from(ld.plusDays(1).toInstant()), Date.class),
					new Criteria<Date>("dayOfBirth", Operation.NE, Date.from(ld.plusDays(2).toInstant()), Date.class),
					new Criteria<Date>("dayOfBirth", Operation.ISNULL, null, Date.class),
					new Criteria<Date>("dayOfBirth", Operation.GT, Date.from(ld.plusDays(3).toInstant()), Date.class),
					new Criteria<Date>("dayOfBirth", Operation.LT, Date.from(ld.plusDays(4).toInstant()), Date.class),
					new Criteria<Date>("dayOfBirth", Operation.GTE, Date.from(ld.plusDays(5).toInstant()), Date.class),
					new Criteria<Date>("dayOfBirth", Operation.LTE, Date.from(ld.plusDays(6).toInstant()), Date.class)
				);
		
		CriteriaSource mockCriteria = mock(CriteriaSource.class);
		when(mockCriteria.getCriteriaFor(any())).thenReturn(sampleCriteria);
		
		
		filterJpaRepository.findAll(mockCriteria);
		
		ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
		verify(em).createQuery(queryCaptor.capture());
		
		String createdQuery = queryCaptor.getValue();
		
		Assert.assertTrue(createdQuery.contains("dayOfBirth = ?1"));
		Assert.assertTrue(createdQuery.contains("dayOfBirth <> ?2"));
		Assert.assertTrue(createdQuery.contains("dayOfBirth is null"));
		Assert.assertTrue(createdQuery.contains("dayOfBirth > ?3"));
		Assert.assertTrue(createdQuery.contains("dayOfBirth < ?4"));
		Assert.assertTrue(createdQuery.contains("dayOfBirth >= ?5"));
		Assert.assertTrue(createdQuery.contains("dayOfBirth <= ?6"));
	}
}
