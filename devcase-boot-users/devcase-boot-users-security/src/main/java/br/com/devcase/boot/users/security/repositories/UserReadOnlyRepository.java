package br.com.devcase.boot.users.security.repositories;


import org.springframework.stereotype.Repository;

import br.com.devcase.boot.users.domain.entities.User;

@Repository
public interface UserReadOnlyRepository extends org.springframework.data.repository.Repository<User, String> {
	User findByName(String name);
	long countByName(String name);

}
