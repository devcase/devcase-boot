package br.com.devcase.boot.starter.jsp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.devcase.boot.jsp.undertow.EnableUndertowJsp;

@Configuration
@EnableUndertowJsp
public class JspAutoConfiguration {
	
	@Bean
	@Order(Ordered.LOWEST_PRECEDENCE - 6)
	public InternalResourceViewResolver jstlViewResolver() {
		InternalResourceViewResolver v = new  InternalResourceViewResolver("/WEB-INF/jsp/", ".jsp");
		return v;
	}
}
