package br.com.devcase.boot.sample.hateoas.controllers;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devcase.boot.sample.crud.entity.Anuncio;
import br.com.devcase.boot.sample.crud.entity.Campanha;
import br.com.devcase.boot.sample.crud.repository.AnuncioRepository;
import br.com.devcase.boot.sample.crud.repository.CampanhaRepository;

@RestController
public class CustomController {
	@Autowired
	private CampanhaRepository campanhaRepository;
	@Autowired
	private AnuncioRepository anuncioRepository;

	@Autowired
	private EntityLinks entityLinks;

	@GetMapping("/api/mydata")
	public HttpEntity<Resources<Resource<Campanha>>> custom() {
		return new HttpEntity<>(new Resources<>(StreamSupport.stream(campanhaRepository.findAll().spliterator(), false)
				.map(it -> new Resource<>(it)).collect(Collectors.toList())));
	}

	@GetMapping("/api/mydata2")
	public HttpEntity<Resources<Resource<Anuncio>>> custom2() {
		return new HttpEntity<>(new Resources<>(
				StreamSupport.stream(anuncioRepository.findAll().spliterator(), false).map(it -> new Resource<> (it, entityLinks.linkForSingleResource(it.getCampanha()).withRel("campanha") )).collect(Collectors.toList())
				));
	}
}
