package br.com.devcase.boot.web.server.autoconfigure;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContainerAutoConfiguration {

    @Configuration
    public class CustomMimeMapper implements EmbeddedServletContainerCustomizer {
        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
            MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
            mappings.add("js", "application/javascript; charset=utf-8");
            container.setMimeMappings(mappings);
        }
    }
}
