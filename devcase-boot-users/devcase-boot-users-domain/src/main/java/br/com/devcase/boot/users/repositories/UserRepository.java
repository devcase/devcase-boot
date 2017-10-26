package br.com.devcase.boot.users.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import br.com.devcase.boot.crud.repository.criteria.CriteriaRepository;
import br.com.devcase.boot.users.domain.entities.User;

@Repository
@RepositoryRestResource(path="users", collectionResourceRel="users")
public interface UserRepository extends CriteriaRepository<User, String> {
	User findByUsername(String username);
	long countByUsername(String username);

}
