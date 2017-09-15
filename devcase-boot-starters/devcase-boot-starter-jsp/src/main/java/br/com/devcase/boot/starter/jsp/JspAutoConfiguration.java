package br.com.devcase.boot.starter.jsp;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import br.com.devcase.boot.jsp.undertow.EnableUndertowJsp;

@Configuration
@EnableUndertowJsp
public class JspAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(name="siteMeshViewResolver")
	public InternalResourceViewResolver jstlViewResolver() {
		InternalResourceViewResolver v = new InternalResourceViewResolver("/WEB-INF/jsp/", ".jsp");
		v.setViewClass(JstlView.class);
		v.setOrder(Ordered.LOWEST_PRECEDENCE - 6);
		return v;
	}
}
