package br.com.devcase.boot.htmldecorator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class HtmlDecoratorConfig {

	@Bean
	public InternalResourceViewResolver htmlDecoratorViewResolver() {
		InternalResourceViewResolver v = new  InternalResourceViewResolver("/WEB-INF/jsp/", ".jsp");
		v.setViewClass(HtmlDecoratorView.class);
		v.setOrder(Ordered.LOWEST_PRECEDENCE - 7);
		return v;
	}


}
