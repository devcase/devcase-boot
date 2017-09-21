package br.com.devcase.boot.crud.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import br.com.devcase.boot.crud.validation.config.ValidationMessagesConfig;

@Configuration
@Import(ValidationMessagesConfig.class)
public class ValidatorAutoConfiguration {

}
