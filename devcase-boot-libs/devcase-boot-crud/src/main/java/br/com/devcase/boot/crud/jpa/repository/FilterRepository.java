package br.com.devcase.boot.crud.jpa.repository;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import br.com.devcase.boot.crud.jpa.repository.query.CriteriaSource;

public interface FilterRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, QueryDslPredicateExecutor<T> {
	Page<T> findAll(CriteriaSource filter, Pageable pageable);
}
