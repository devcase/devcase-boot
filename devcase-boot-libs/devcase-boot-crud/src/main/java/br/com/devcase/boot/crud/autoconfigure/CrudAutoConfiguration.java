package br.com.devcase.boot.crud.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/br/com/devcase/boot/crud/properties/crud.properties")
public class CrudAutoConfiguration {

}
