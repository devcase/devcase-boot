package br.com.devcase.boot.users.webadmin.repositories.oauth2;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.devcase.boot.crud.repository.criteria.CriteriaRepository;
import br.com.devcase.boot.users.domain.entities.oauth2.OAuth2Client;

@RepositoryRestResource(path="oauth2clients")
public interface OAuth2ClientRepository extends CriteriaRepository<OAuth2Client, String>{

	int countByClientId(String clientId);
}
