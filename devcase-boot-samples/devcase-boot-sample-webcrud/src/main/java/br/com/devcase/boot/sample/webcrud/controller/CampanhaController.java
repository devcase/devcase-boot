package br.com.devcase.boot.sample.webcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.devcase.boot.sample.crud.entity.Campanha;
import br.com.devcase.boot.sample.crud.repository.CampanhaRepository;
import br.com.devcase.boot.webcrud.CrudController;

@Controller
@RequestMapping("/campanha")
public class CampanhaController extends CrudController<Campanha, String> {

	@Autowired
	public CampanhaController(CampanhaRepository repository) {
		super(Campanha.class, repository, "campanha");
	}

	
}
