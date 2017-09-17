package br.com.devcase.boot.sample.crud.repository;

import org.springframework.stereotype.Repository;

import br.com.devcase.boot.crud.jpa.repository.query.CriteriaRepository;
import br.com.devcase.boot.sample.crud.entity.Campanha;

@Repository
public interface CampanhaRepository extends CriteriaRepository<Campanha, String> {

}
