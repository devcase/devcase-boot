package br.com.devcase.boot.users.webadmin.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import br.com.devcase.boot.users.domain.entities.UserPermission;

@Repository
@PreAuthorize("hasRole('ADMIN_USERS')")
@RepositoryRestResource(path="user-permissions", collectionResourceRel="user-permissions")
public interface UserPermissionRepository extends CrudRepository<UserPermission, String> {

}
