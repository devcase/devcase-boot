package br.com.devcase.boot.users.webadmin.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.devcase.boot.crud.repository.criteria.CriteriaRepository;
import br.com.devcase.boot.users.domain.entities.UserGroup;

@RepositoryRestResource(path="groups", collectionResourceRel="groups")
@PreAuthorize("hasRole('ROLE_ADMIN_USERS')")
public interface UserGroupRepository extends CriteriaRepository<UserGroup, String> {

}
