package br.com.devcase.boot.users.sample.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.devcase.boot.users.security.config.EnableWebFormAuthentication;

@SpringBootApplication
@EnableWebFormAuthentication
public class SampleUsersWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleUsersWebAppApplication.class, args);
	}

}
