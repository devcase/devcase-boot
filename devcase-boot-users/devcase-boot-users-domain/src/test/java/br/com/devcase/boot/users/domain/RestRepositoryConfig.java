package br.com.devcase.boot.users.domain;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.devcase.boot.users.domain.autoconfigure.DevcaseUsersAutoConfiguration;

@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@Import({DevcaseUsersAutoConfiguration.class, RepositoryRestMvcConfiguration.class})
public class RestRepositoryConfig {

}
