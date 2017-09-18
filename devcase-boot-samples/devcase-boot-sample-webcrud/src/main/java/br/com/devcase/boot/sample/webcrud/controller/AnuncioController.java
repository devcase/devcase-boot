package br.com.devcase.boot.sample.webcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.devcase.boot.sample.crud.entity.Anuncio;
import br.com.devcase.boot.sample.crud.repository.AnuncioRepository;
import br.com.devcase.boot.webcrud.CrudController;

@Controller
@RequestMapping("/anuncio")
public class AnuncioController extends CrudController<Anuncio, String>{

	@Autowired
	public AnuncioController(AnuncioRepository repository) {
		super(Anuncio.class, repository, "anuncio");
	}

}
