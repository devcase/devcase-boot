package br.com.devcase.boot.sample.hateoas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.config.EnableEntityLinks;

import br.com.devcase.boot.sample.crud.config.SampleCrudConfig;

@SpringBootApplication
@Import(SampleCrudConfig.class)
@EnableEntityLinks
public class SampleHateoasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleHateoasApplication.class, args);
	}
}
