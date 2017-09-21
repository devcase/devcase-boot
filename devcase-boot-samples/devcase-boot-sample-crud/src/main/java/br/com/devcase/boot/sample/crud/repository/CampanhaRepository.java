package br.com.devcase.boot.sample.crud.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.devcase.boot.crud.repository.criteria.CriteriaRepository;
import br.com.devcase.boot.sample.crud.entity.Campanha;

@Repository
public interface CampanhaRepository extends CriteriaRepository<Campanha, String> {

	List<Campanha> findByNome(String nome);
	List<Campanha> findByNomeStartingWith(String nome);

}
