package br.com.devcase.boot.users.webadmin.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import br.com.devcase.boot.crud.repository.criteria.CriteriaRepository;
import br.com.devcase.boot.users.domain.entities.UserGroup;

@Repository
@PreAuthorize("hasRole('ADMIN_USERS')")
@RepositoryRestResource(path="groups", collectionResourceRel="groups")
public interface UserGroupRepository extends CriteriaRepository<UserGroup, String> {

}
