package br.com.devcase.boot.users.domain.repositories;

import java.io.Serializable;

import br.com.devcase.boot.crud.repository.criteria.CriteriaRepository;
import br.com.devcase.boot.users.domain.entities.User;

public interface UserRepository extends CriteriaRepository<User, Serializable> {

}
