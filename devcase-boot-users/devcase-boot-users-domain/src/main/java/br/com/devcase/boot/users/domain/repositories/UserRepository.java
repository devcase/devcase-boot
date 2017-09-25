package br.com.devcase.boot.users.domain.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.devcase.boot.crud.repository.criteria.CriteriaRepository;
import br.com.devcase.boot.users.domain.entities.User;

@RepositoryRestResource(path="users", collectionResourceRel="users")
public interface UserRepository extends CriteriaRepository<User, Long> {

}
