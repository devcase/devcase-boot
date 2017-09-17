package br.com.devcase.boot.webcrud.criteria;

import java.util.List;

import br.com.devcase.boot.crud.jpa.repository.query.Criteria;

public interface CriteriaSource {

	<T> List<Criteria> getCriteria(Class<T> domainClass);
}
