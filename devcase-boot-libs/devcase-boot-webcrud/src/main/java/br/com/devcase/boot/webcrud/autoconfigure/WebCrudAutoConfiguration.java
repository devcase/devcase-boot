package br.com.devcase.boot.webcrud.autoconfigure;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.devcase.boot.webcrud.criteria.CriteriaSourceArgumentResolver;

@Configuration
@PropertySource(value="classpath:/br/com/devcase/boot/webcrud/properties/web-crud.properties")
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
		static class WebMvcCriteriaConfigurer implements WebMvcConfigurer {

			@Autowired
			private CriteriaSourceArgumentResolver criteriaSourceArgumentResolver;

			@Override
			public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
				argumentResolvers.add(criteriaSourceArgumentResolver);
			}
		}
	}
	
	@Configuration
	@ConditionalOnBean(value=EntityManagerFactory.class)
	static class ExposeIds extends RepositoryRestConfigurerAdapter {
		@Autowired
		private EntityManagerFactory emf;

		@Override
		public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
			Set<EntityType<?>> entities = emf.getMetamodel().getEntities();
			config.exposeIdsFor(entities.stream().map(e -> e.getJavaType()).toArray(size -> new Class<?>[size]));
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
