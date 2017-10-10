package br.com.devcase.boot.users.security.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.devcase.boot.users.domain.entities.User;
import br.com.devcase.boot.users.domain.entities.UserPermission;

@org.springframework.stereotype.Repository
@RepositoryRestResource(exported=false)
public interface UserPermissionsReadOnlyRepository extends Repository<UserPermission, String>{
	@Query("select up from UserPermission up where up.user = ?1 and (up.validUntil is null or up.validUntil > CURRENT_DATE )")
	List<UserPermission> findValidByUser(User user);
}
