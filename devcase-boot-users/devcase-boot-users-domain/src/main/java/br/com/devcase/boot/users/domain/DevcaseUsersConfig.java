package br.com.devcase.boot.users.domain;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.com.devcase.boot.crud.jpa.repository.support.CriteriaJpaRepository;

@Configuration
@EntityScan
@EnableJpaRepositories(repositoryBaseClass=CriteriaJpaRepository.class)
public class DevcaseUsersConfig {

}
