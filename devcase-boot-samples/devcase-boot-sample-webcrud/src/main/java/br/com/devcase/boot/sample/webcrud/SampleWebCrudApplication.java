package br.com.devcase.boot.sample.webcrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import br.com.devcase.boot.sample.crud.config.SampleCrudConfig;

@SpringBootApplication
@Import(SampleCrudConfig.class)
public class SampleWebCrudApplication {
	public static void main(String[] args) {
		SpringApplication.run(SampleWebCrudApplication.class, args);
	}

}
