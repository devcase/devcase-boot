package br.com.devcase.boot.sample.crud.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.devcase.boot.sample.crud.entity.Campanha;
import br.com.devcase.boot.sample.crud.repository.CampanhaRepository;

@Configuration
@EntityScan(basePackageClasses=Campanha.class)
@EnableJpaRepositories(basePackageClasses=CampanhaRepository.class)
public class SampleCrudConfig {
	
}
