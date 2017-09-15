package br.com.devcase.boot.sitemesh;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.webapp.SiteMeshFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class SiteMeshConfig {

	@Bean
	public InternalResourceViewResolver siteMeshViewResolver() {
		InternalResourceViewResolver v = new  InternalResourceViewResolver("/WEB-INF/jsp/", ".jsp");
		v.setViewClass(SiteMeshView.class);
		v.setOrder(Ordered.LOWEST_PRECEDENCE - 5);
		return v;
	}
	
	@Bean
	public SiteMeshFilter siteMeshFilter() {
		return (SiteMeshFilter) new SiteMeshFilterBuilder()
				.create();
	}


}
