package br.com.devcase.boot.crud.jpa.repository.query;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CriteriaRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID>, QueryDslPredicateExecutor<T> {
	Page<T> findAll(List<Criteria> criteria, Pageable pageable);
	List<T> findAll(List<Criteria> criteria);
	Page<T> findAll(Pageable pageable, Criteria... criteria);
	List<T> findAll(Criteria... criteria);
}
