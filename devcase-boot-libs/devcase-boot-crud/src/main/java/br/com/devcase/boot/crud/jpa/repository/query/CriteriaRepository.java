package br.com.devcase.boot.crud.jpa.repository.query;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface CriteriaRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, QueryDslPredicateExecutor<T> {
	Page<T> findAll(List<Criteria> criteria, Pageable pageable);
	Page<T> findAll(Pageable pageable, Criteria<?>... criteria);
	List<T> findAll(Criteria<?>... criteria);
}
