package br.com.devcase.boot.starter.jsp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.devcase.boot.jsp.undertow.EnableUndertowJsp;

@Configuration
@EnableUndertowJsp
public class JspAutoConfiguration {
	
	@Bean
	public InternalResourceViewResolver jstlViewResolver() {
		InternalResourceViewResolver v = new  InternalResourceViewResolver("/WEB-INF/jsp/", ".jsp");
		v.setOrder(Ordered.LOWEST_PRECEDENCE - 6);
		return v;
	}
}
