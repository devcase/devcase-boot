package br.com.devcase.boot.crud.repository;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.devcase.boot.crud.jpa.repository.support.CriteriaJpaRepository;
import br.com.devcase.boot.crud.repository.testdomain.Magazine;
import br.com.devcase.boot.crud.repository.testdomain.MagazineRepository;

@Configuration
@EnableJpaRepositories(repositoryBaseClass=CriteriaJpaRepository.class, basePackageClasses=MagazineRepository.class)
@EntityScan(basePackageClasses=Magazine.class)
@EnableAutoConfiguration
public class RepositoryTestConfig {

}
