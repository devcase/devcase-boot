package br.com.devcase.boot.dbmigration.ddl;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.devcase.boot.dbmigration.ddl.entities.ExampleEntity;
import br.com.devcase.dbmigration.ddl.DdlTool;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackageClasses=ExampleEntity.class)
public class IntegrationTestConfiguration {

	@Bean
	public DdlTool ddl() {
		return new DdlTool();
	}
}
