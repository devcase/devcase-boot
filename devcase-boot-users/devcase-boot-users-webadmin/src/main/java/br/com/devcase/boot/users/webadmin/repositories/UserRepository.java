package br.com.devcase.boot.users.webadmin.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.devcase.boot.crud.repository.criteria.CriteriaRepository;
import br.com.devcase.boot.users.domain.entities.User;

@PreAuthorize("hasRole('ADMIN_USERS')")
@RepositoryRestResource(path="users", collectionResourceRel="users")
public interface UserRepository extends CriteriaRepository<User, String> {
	User findByName(String name);
	long countByName(String name);

}
