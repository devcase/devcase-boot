package br.com.devcase.boot.sample.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.devcase.boot.sample.crud.entity.Campanha;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, String> {

}
