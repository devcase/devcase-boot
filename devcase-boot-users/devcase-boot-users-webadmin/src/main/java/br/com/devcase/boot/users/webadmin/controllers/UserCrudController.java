package br.com.devcase.boot.users.webadmin.controllers;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.devcase.boot.users.domain.entities.User;
import br.com.devcase.boot.users.domain.repositories.UserRepository;
import br.com.devcase.boot.webcrud.CrudController;

@Controller
@RequestMapping("/users")
public class UserCrudController extends CrudController<User, Serializable> {

	public UserCrudController(UserRepository repository) {
		super(User.class, repository, "users");
	}

}
