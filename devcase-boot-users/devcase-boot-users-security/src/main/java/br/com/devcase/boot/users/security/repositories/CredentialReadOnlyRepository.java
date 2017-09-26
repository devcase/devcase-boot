package br.com.devcase.boot.users.security.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.devcase.boot.users.domain.entities.Credential;
import br.com.devcase.boot.users.domain.entities.PasswordCredential;
import br.com.devcase.boot.users.domain.entities.User;

@Repository
public interface CredentialReadOnlyRepository extends org.springframework.data.repository.Repository<Credential, String> {
	List<Credential> findByUser(User user);
	Credential findByUserAndDtype(User user, String dtype);
	
	@Query(value="select p.password from PasswordCredential p where p.user = ?1")
	String findPasswordByUser(User user);

	@Query(value="select p from PasswordCredential p where p.user = ?1")
	PasswordCredential findPasswordCredentialByUser(User user);

	@Query(value="select p from PasswordCredential p where p.user.name = ?1")
	PasswordCredential findPasswordCredentialByUsername(String username);

}
