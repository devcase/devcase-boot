package br.com.devcase.boot.webcrud.autoconfigure;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.devcase.boot.webcrud.criteria.CriteriaSourceArgumentResolver;

@Configuration
@PropertySource(value = "classpath:/br/com/devcase/boot/webcrud/properties/web-crud.properties")
public class WebCrudAutoConfiguration {

	/**
	 * Beans necessários para o suporte a CriteriaRepository e CrudController
	 * 
	 * @author hirata
	 *
	 */
	@Configuration
	@AutoConfigureBefore(WebMvcAutoConfiguration.class)
	public static class CriteriaRepositorySupportConfiguration {
		@Bean
		public CriteriaSourceArgumentResolver criteriaSourceArgumentResolver() {
			return new CriteriaSourceArgumentResolver();
		}

		@Configuration
		public static class WebMvcCriteriaConfigurer implements WebMvcConfigurer {

			@Autowired
			private CriteriaSourceArgumentResolver criteriaSourceArgumentResolver;

			@Override
			public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
				argumentResolvers.add(criteriaSourceArgumentResolver);
			}
		}
	}

	@Configuration
	static class ExposeIdsRestMvcConfiguration {

		@Configuration
		public static class ExposeIdsConfigurer extends RepositoryRestConfigurerAdapter {
			@Autowired(required=false)
			private EntityManagerFactory emf;

			@Override
			public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
				if(emf == null) return;
				Set<EntityType<?>> entities = emf.getMetamodel().getEntities();
				config.exposeIdsFor(entities.stream().map(e -> e.getJavaType()).toArray(size -> new Class<?>[size]));
			}

		}

	}

}
