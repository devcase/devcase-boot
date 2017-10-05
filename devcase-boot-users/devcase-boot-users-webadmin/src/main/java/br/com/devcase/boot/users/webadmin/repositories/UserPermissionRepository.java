package br.com.devcase.boot.users.webadmin.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import br.com.devcase.boot.users.domain.entities.UserPermission;

@Repository
@PreAuthorize("hasRole('ROLE_ADMIN_USERS')")
public interface UserPermissionRepository extends CrudRepository<UserPermission, String> {

}
