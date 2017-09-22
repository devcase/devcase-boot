package br.com.devcase.boot.crud.repository.testdomain;

import org.springframework.stereotype.Repository;

import br.com.devcase.boot.crud.repository.criteria.CriteriaRepository;

@Repository
public interface PublisherRepository extends CriteriaRepository<Publisher, Long>{

}
