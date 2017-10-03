package br.com.devcase.boot.users.webadmin.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.devcase.boot.crud.repository.criteria.CriteriaRepository;
import br.com.devcase.boot.users.domain.entities.GroupPermission;

@RepositoryRestResource(path="group-permissions", collectionResourceRel="group-permissions")
@PreAuthorize("hasRole('ROLE_ADMIN_USERS')")
public interface GroupPermissionRepository extends CriteriaRepository<GroupPermission, String> {

}
