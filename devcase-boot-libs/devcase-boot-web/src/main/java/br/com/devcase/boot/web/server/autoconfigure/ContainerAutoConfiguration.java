package br.com.devcase.boot.web.server.autoconfigure;

import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContainerAutoConfiguration {

    @Configuration
    public class CustomMimeMapper implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

		@Override
		public void customize(ConfigurableServletWebServerFactory server) {
            MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
            mappings.add("js", "application/javascript; charset=utf-8");
            server.setMimeMappings(mappings);
			
		}
    }
}
