package br.com.devcase.boot.crud.repository.criteria;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.devcase.boot.crud.repository.IdentifierExtractor;
import br.com.devcase.boot.crud.repository.PropertyUpdate;

public interface CriteriaRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID>, IdentifierExtractor<T, ID>, PropertyUpdate<ID> {
	Page<T> findAll(List<Criteria> criteria, Pageable pageable);
	List<T> findAll(List<Criteria> criteria);
	Page<T> findAll(Pageable pageable, Criteria... criteria);
	List<T> findAll(Criteria... criteria);
	
}
