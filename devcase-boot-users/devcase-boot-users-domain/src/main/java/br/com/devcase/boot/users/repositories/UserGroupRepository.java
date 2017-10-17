package br.com.devcase.boot.users.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import br.com.devcase.boot.crud.repository.criteria.CriteriaRepository;
import br.com.devcase.boot.users.domain.entities.UserGroup;

@Repository
@RepositoryRestResource(path="groups", collectionResourceRel="groups")
public interface UserGroupRepository extends CriteriaRepository<UserGroup, String> {

}
