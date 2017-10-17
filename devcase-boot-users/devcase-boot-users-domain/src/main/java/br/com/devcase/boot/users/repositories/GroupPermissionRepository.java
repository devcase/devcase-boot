package br.com.devcase.boot.users.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import br.com.devcase.boot.crud.repository.criteria.CriteriaRepository;
import br.com.devcase.boot.users.domain.entities.GroupPermission;
import br.com.devcase.boot.users.domain.entities.User;

@Repository
@RepositoryRestResource(path="group-permissions", collectionResourceRel="group-permissions")
public interface GroupPermissionRepository extends CriteriaRepository<GroupPermission, String> {
	@Query("select gp from GroupPermission gp where gp.group.id in (select g.id from User u join u.groups g where u = ?1) and (gp.validUntil is null or gp.validUntil > CURRENT_DATE )")
	List<GroupPermission> findValidByUser(User user);

}
