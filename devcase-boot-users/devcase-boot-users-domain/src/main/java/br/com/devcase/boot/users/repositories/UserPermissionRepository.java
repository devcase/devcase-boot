package br.com.devcase.boot.users.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import br.com.devcase.boot.users.domain.entities.User;
import br.com.devcase.boot.users.domain.entities.UserPermission;

@Repository
@RepositoryRestResource(path="user-permissions", collectionResourceRel="user-permissions")
public interface UserPermissionRepository extends CrudRepository<UserPermission, String> {
	@Query("select up from UserPermission up where up.user = ?1 and (up.validUntil is null or up.validUntil > CURRENT_DATE )")
	List<UserPermission> findValidByUser(User user);

}
