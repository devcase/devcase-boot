package br.com.devcase.boot.webcrud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.devcase.boot.webcrud.criteria.CriteriaSourceArgumentResolver;

@Configuration
public class WebCrudAutoConfiguration {

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
