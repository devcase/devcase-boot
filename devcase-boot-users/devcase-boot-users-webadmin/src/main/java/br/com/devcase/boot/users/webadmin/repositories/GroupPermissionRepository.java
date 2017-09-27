package br.com.devcase.boot.users.webadmin.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.devcase.boot.crud.repository.criteria.CriteriaRepository;
import br.com.devcase.boot.users.domain.entities.GroupPermission;

@RepositoryRestResource(path="group-permissions", collectionResourceRel="group-permissions")
public interface GroupPermissionRepository extends CriteriaRepository<GroupPermission, String> {

}
