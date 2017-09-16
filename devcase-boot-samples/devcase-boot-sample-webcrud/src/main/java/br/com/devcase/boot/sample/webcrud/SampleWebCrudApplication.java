package br.com.devcase.boot.sample.webcrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import br.com.devcase.boot.labels.EnableDevcaseLabels;
import br.com.devcase.boot.sample.crud.config.SampleCrudConfig;
import br.com.devcase.boot.sitemesh.EnableSiteMesh;

@SpringBootApplication
@EnableSiteMesh
@EnableDevcaseLabels
@Import(SampleCrudConfig.class)
public class SampleWebCrudApplication {
	public static void main(String[] args) {
		SpringApplication.run(SampleWebCrudApplication.class, args);
	}

}
