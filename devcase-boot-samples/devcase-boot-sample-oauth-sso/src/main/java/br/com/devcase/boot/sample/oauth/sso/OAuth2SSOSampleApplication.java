package br.com.devcase.boot.sample.oauth.sso;

import java.text.MessageFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@EnableWebSecurity
public class OAuth2SSOSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2SSOSampleApplication.class, args);
	}

	@Controller
	static class HomeController {
		
		@RequestMapping("/")
		@ResponseBody
		ResponseEntity<String> index() {
			SecurityContext securityContext = SecurityContextHolder.getContext();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.TEXT_HTML);
			String body = MessageFormat.format("<html><body><h1>Hello, {0}</h1></body></html>", securityContext.getAuthentication().getPrincipal());
			return new ResponseEntity<String>(body.toString(), headers, HttpStatus.OK);
		}
	}

}
