package br.com.devcase.boot.sample.sitemesh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.devcase.boot.sitemesh.EnableSiteMesh;

@SpringBootApplication
@EnableSiteMesh
@Controller
public class SiteMeshSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiteMeshSampleApplication.class, args);
	}
	
	@RequestMapping(value= {"", "/"})
	public String index() {
		return "index";
	}

}
