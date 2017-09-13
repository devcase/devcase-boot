package br.com.devcase.boot.jsp.undertow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UndertowJspConfiguration {
	@Bean
	public JspForUndertowContainerCustomizer jspForUndertowContainerCustomizer() {
		return new JspForUndertowContainerCustomizer();
	}
	
	@Bean
	public ResourceManagerUndertowContainerCustomizer resourceManagerUndertowContainerCustomizer() {
		return new ResourceManagerUndertowContainerCustomizer();
	}
}
