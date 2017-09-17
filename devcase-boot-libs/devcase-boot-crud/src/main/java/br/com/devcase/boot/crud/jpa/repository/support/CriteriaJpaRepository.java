package br.com.devcase.boot.crud.jpa.repository.support;

import java.io.Serializable;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;

import br.com.devcase.boot.crud.jpa.repository.query.Criteria;
import br.com.devcase.boot.crud.jpa.repository.query.CriteriaRepository;

public class CriteriaJpaRepository<T, ID extends Serializable> extends QueryDslJpaRepository<T, ID>
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
	
	public CriteriaJpaRepository(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager, resolver);
	}


	@Override
	public Page<T> findAll(Pageable pageable, Criteria<?>... criteria) {
		Predicate predicate = createPredicate(criteria);
		return predicate == null ? findAll(pageable) : findAll(predicate, pageable);
	}

	@Override
	public Page<T> findAll(List<Criteria<?>> criteria, Pageable pageable) {
		Predicate predicate = createPredicate(criteria.toArray(new Criteria[0]));
		return predicate == null ? findAll(pageable) : findAll(predicate, pageable);
	}

	@Override
	public List<T> findAll(Criteria<?>... criteria) {
		Predicate predicate = createPredicate(criteria);
		return predicate == null ? findAll() : findAll(predicate);
	}

	protected BooleanExpression createPredicate(Criteria<?>... criteria) {
		
		//translate the criteria into predicates for QueryDSL
		PathBuilder<T> parentPath = CriteriaJpaRepository.createPath(getDomainClass());
		BooleanExpression predicate = null;
		for (Criteria<?> crit : criteria) {
			if(predicate == null) {
				predicate = createPredicate(parentPath, crit);
			} else {
				predicate = predicate.and(createPredicate(parentPath, crit));
			}
		}
		return predicate;
	}

	@SuppressWarnings("unchecked")
	protected BooleanExpression createPredicate(PathBuilder<T> pathBuilder, Criteria<?> criteria) {
		if (Number.class.isAssignableFrom(criteria.getPropertyType())) {
			return createNumberPredicate(pathBuilder, (Criteria<Long>) criteria);
		}
		
		if(Temporal.class.isAssignableFrom(criteria.getPropertyType()) || Date.class.isAssignableFrom(criteria.getPropertyType())) {
			return createTemporalPredicate(pathBuilder, (Criteria<Comparable<?>>) criteria);
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
			throw new RuntimeException("Invalid operation " + criteria.getOperation() + " for property " + criteria.getProperty());
		}
	}

	@SuppressWarnings("rawtypes")
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
			throw new RuntimeException("Invalid operation " + criteria.getOperation() + " for property " + criteria.getProperty());
		}
	}

	@SuppressWarnings("rawtypes")
	protected <P extends Comparable> BooleanExpression createTemporalPredicate(PathBuilder<T> pathBuilder,
			Criteria<P> criteria) {
		DatePath<P> path = pathBuilder.getDate(criteria.getProperty(), criteria.getPropertyType());

		switch (criteria.getOperation()) {
		case EQ:
			return path.eq(criteria.getValue());
		case ISNULL:
			return path.isNull();
		case NE:
			return path.ne(criteria.getValue());
		case GT:
			return path.gt(criteria.getValue());
		case LT:
			return path.lt(criteria.getValue());
		case GTE:
			return path.goe(criteria.getValue());
		case LTE:
			return path.loe(criteria.getValue());
		default:
			throw new RuntimeException("Invalid operation " + criteria.getOperation() + " for property " + criteria.getProperty());
		}
	}
}
