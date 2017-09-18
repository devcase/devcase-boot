package br.com.devcase.boot.sample.crud.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.devcase.boot.crud.jpa.repository.query.CriteriaRepository;
import br.com.devcase.boot.sample.crud.entity.Anuncio;
import br.com.devcase.boot.sample.crud.entity.Campanha;

@Repository
public interface AnuncioRepository extends CriteriaRepository<Anuncio, String> {

	List<Anuncio> findByCampanha(Campanha campanha);
}
