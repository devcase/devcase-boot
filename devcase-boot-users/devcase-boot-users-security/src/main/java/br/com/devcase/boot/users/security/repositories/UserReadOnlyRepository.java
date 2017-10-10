package br.com.devcase.boot.users.security.repositories;


import java.util.Optional;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import br.com.devcase.boot.users.domain.entities.User;

@Repository
@RepositoryRestResource(exported=false)
public interface UserReadOnlyRepository extends org.springframework.data.repository.Repository<User, String> {
	User findByName(String name);
	long countByName(String name);
	Optional<User> findById(String userId);

}
