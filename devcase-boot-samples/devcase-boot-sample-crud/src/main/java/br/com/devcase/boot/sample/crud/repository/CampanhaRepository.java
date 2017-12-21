package br.com.devcase.boot.sample.crud.repository;

import java.util.List;

import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import br.com.devcase.boot.crud.repository.criteria.CriteriaRepository;
import br.com.devcase.boot.sample.crud.entity.Campanha;

@Repository
@RestResource
public interface CampanhaRepository extends CriteriaRepository<Campanha, String> {

	List<Campanha> findByNome(String nome);
	List<Campanha> findByNomeStartingWith(String nome);

}
