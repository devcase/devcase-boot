package br.com.devcase.boot.crud.jpa.repository.support;

import java.io.Serializable;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;

import br.com.devcase.boot.crud.repository.criteria.Criteria;
import br.com.devcase.boot.crud.repository.criteria.CriteriaRepository;

public class CriteriaJpaRepository<T, ID extends Serializable> extends QuerydslJpaRepository<T, ID>
		implements CriteriaRepository<T, ID> {

	static EntityPathResolver resolver;
	static {
		resolver = new EntityPathResolver() {

			@Override
			public <T> EntityPath<T> createPath(Class<T> domainClass) {
				return CriteriaJpaRepository.createPath(domainClass);
			}
		};
	}

	static <T> PathBuilder<T> createPath(Class<T> domainClass) {
		return new PathBuilder<T>(domainClass, domainClass.getSimpleName().toLowerCase().concat("_1"));
	}

	private final EntityManager em;
	private final JpaEntityInformation<T, ID> entityInformation;

	public CriteriaJpaRepository(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager, resolver);
		this.em = entityManager;
		this.entityInformation = entityInformation;
	}

	@Override
	public Page<T> findAll(Pageable pageable, @SuppressWarnings("rawtypes") Criteria... criteria) {
		Predicate predicate = createPredicate(criteria);
		return predicate == null ? findAll(pageable) : findAll(predicate, pageable);
	}

	@Override
	public List<T> findAll(@SuppressWarnings("rawtypes") List<Criteria> criteria) {
		Predicate predicate = createPredicate(criteria.toArray(new Criteria[0]));
		return predicate == null ? findAll() : findAll(predicate);
	}

	@Override
	public Page<T> findAll(@SuppressWarnings("rawtypes") List<Criteria> criteria, Pageable pageable) {
		Predicate predicate = createPredicate(criteria.toArray(new Criteria[0]));
		return predicate == null ? findAll(pageable) : findAll(predicate, pageable);
	}

	@Override
	public List<T> findAll(@SuppressWarnings("rawtypes") Criteria... criteria) {
		Predicate predicate = createPredicate(criteria);
		return predicate == null ? findAll() : findAll(predicate);
	}
	
	@SuppressWarnings("rawtypes") 
	protected BooleanExpression createPredicate(Criteria... criteria) {

		// translate the criteria into predicates for QueryDSL
		PathBuilder<T> parentPath = CriteriaJpaRepository.createPath(getDomainClass());
		BooleanExpression predicate = null;
		for (Criteria crit : criteria) {
			if (predicate == null) {
				predicate = createPredicate(parentPath, crit);
			} else {
				predicate = predicate.and(createPredicate(parentPath, crit));
			}
		}
		return predicate;
	}

	@SuppressWarnings("rawtypes") 
	protected BooleanExpression createPredicate(PathBuilder<T> pathBuilder, Criteria criteria) {
		if (Number.class.isAssignableFrom(criteria.getPropertyType())) {
			return createNumberPredicate(pathBuilder, (Criteria) criteria);
		}

		if (Temporal.class.isAssignableFrom(criteria.getPropertyType())
				|| Date.class.isAssignableFrom(criteria.getPropertyType())) {
			return createTemporalPredicate(pathBuilder, (Criteria) criteria);
		}
		PathBuilder<Object> path = pathBuilder.get(criteria.getProperty());

		switch (criteria.getOperation()) {
		case EQ:
			return path.eq(criteria.getValue());
		case ISNULL:
			return path.isNull();
		case NE:
			return path.ne(criteria.getValue());
		default:
			throw new RuntimeException(
					"Invalid operation " + criteria.getOperation() + " for property " + criteria.getProperty());
		}
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	protected <P extends Number & Comparable> BooleanExpression createNumberPredicate(PathBuilder<T> pathBuilder,
			Criteria criteria) {
		NumberPath path = pathBuilder.getNumber(criteria.getProperty(), criteria.getPropertyType());

		switch (criteria.getOperation()) {
		case EQ:
			return path.eq(criteria.getValue());
		case ISNULL:
			return path.isNull();
		case NE:
			return path.ne(criteria.getValue());
		case GT:
			return path.gt((Number) criteria.getValue());
		case LT:
			return path.lt((Number) criteria.getValue());
		case GTE:
			return path.goe((Number) criteria.getValue());
		case LTE:
			return path.loe((Number) criteria.getValue());
		default:
			throw new RuntimeException(
					"Invalid operation " + criteria.getOperation() + " for property " + criteria.getProperty());
		}
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	protected <P extends Comparable> BooleanExpression createTemporalPredicate(PathBuilder<T> pathBuilder,
			Criteria criteria) {
		DatePath path = pathBuilder.getDate(criteria.getProperty(), criteria.getPropertyType());

		switch (criteria.getOperation()) {
		case EQ:
			return path.eq(criteria.getValue());
		case ISNULL:
			return path.isNull();
		case NE:
			return path.ne(criteria.getValue());
		case GT:
			return path.gt((Comparable) criteria.getValue());
		case LT:
			return path.lt((Comparable) criteria.getValue());
		case GTE:
			return path.goe((Comparable) criteria.getValue());
		case LTE:
			return path.loe((Comparable) criteria.getValue());
		default:
			throw new RuntimeException(
					"Invalid operation " + criteria.getOperation() + " for property " + criteria.getProperty());
		}
	}

	@Override
	public ID extractIdentifier(T value) {
		return entityInformation.getId(value);
	}

	@Override
	public <P> void updateProperty(ID id, String propertyName, P value) {
//		//Não usar CriteriaUpdate - não vai salvar no envers
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//		CriteriaUpdate<T> criteria = cb.createCriteriaUpdate(getDomainClass());
//		Root<T> root = criteria.from(getDomainClass());
//		criteria.set(propertyName, value);
//		criteria.where(cb.equal(root.get("id"), id));
//		return em.createQuery(criteria).executeUpdate();
		
		Optional<T> optional = findById(id);
		if(optional.isPresent()) {
			PropertyAccessorFactory.forBeanPropertyAccess(optional.get()).setPropertyValue(propertyName, value);
			em.flush();
			
		}
	}

}
