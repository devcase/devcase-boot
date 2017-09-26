package br.com.devcase.boot.users.webadmin.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.devcase.boot.users.domain.entities.UserPermission;

@Repository
public interface UserPermissionRepository extends CrudRepository<UserPermission, String> {

}
