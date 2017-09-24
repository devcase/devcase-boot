package br.com.devcase.boot.webcrud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.devcase.boot.webcrud.criteria.CriteriaSourceArgumentResolver;

@Configuration
public class WebCrudAutoConfiguration {

	/**
	 * Beans necessários para o suporte a CriteriaRepository e CrudController
	 * 
	 * @author hirata
	 *
	 */
	@Configuration
	static class CriteriaRepositorySupportConfiguration {
		@Bean
		public CriteriaSourceArgumentResolver criteriaSourceArgumentResolver() {
			return new CriteriaSourceArgumentResolver();
		}

		@Configuration
		static class WebMvcConfigurer extends WebMvcConfigurerAdapter {

			@Autowired
			private CriteriaSourceArgumentResolver criteriaSourceArgumentResolver;

			@Override
			public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
				argumentResolvers.add(criteriaSourceArgumentResolver);
			}
		}
	}

//	@Configuration
//	static class JsonFormatCustomizerConfiguration {
//		@Bean
//		public Jackson2ObjectMapperBuilder objectMapperBuilder() {
//			Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//			return builder;
//		}
//	}
}
