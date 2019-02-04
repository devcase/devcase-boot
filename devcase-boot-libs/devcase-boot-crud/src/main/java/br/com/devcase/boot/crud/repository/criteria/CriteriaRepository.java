package br.com.devcase.boot.crud.repository.criteria;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.devcase.boot.crud.repository.IdentifierExtractor;
import br.com.devcase.boot.crud.repository.PropertyUpdate;

public interface CriteriaRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID>,
		IdentifierExtractor<T, ID>, PropertyUpdate<ID>, JpaSpecificationExecutor<T> {
	Page<T> findAll(@SuppressWarnings("rawtypes") List<Criteria> criteria, Pageable pageable);

	List<T> findAll(@SuppressWarnings("rawtypes") List<Criteria> criteria);

	Page<T> findAll(Pageable pageable, @SuppressWarnings("rawtypes") Criteria... criteria);

	List<T> findAll(@SuppressWarnings("rawtypes") Criteria... criteria);

}
