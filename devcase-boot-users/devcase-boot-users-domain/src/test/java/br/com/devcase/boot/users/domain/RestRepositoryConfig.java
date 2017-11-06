package br.com.devcase.boot.users.domain;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.devcase.boot.users.domain.autoconfigure.DevcaseUsersAutoConfiguration;
import br.com.devcase.boot.webcrud.autoconfigure.WebCrudAutoConfiguration;

@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@Import({DevcaseUsersAutoConfiguration.class, WebCrudAutoConfiguration.class, RepositoryRestMvcAutoConfiguration.class})
public class RestRepositoryConfig {

}
