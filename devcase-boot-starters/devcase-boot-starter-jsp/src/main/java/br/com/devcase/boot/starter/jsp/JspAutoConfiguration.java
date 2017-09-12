package br.com.devcase.boot.starter.jsp;

import java.io.IOException;
import java.util.HashMap;

import org.apache.jasper.deploy.JspPropertyGroup;
import org.apache.jasper.deploy.TagLibraryInfo;
import org.springframework.boot.autoconfigure.websocket.WebSocketContainerCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.devcase.boot.jsp.undertow.TldLocator;
import io.undertow.jsp.HackInstanceManager;
import io.undertow.jsp.JspServletBuilder;
import io.undertow.servlet.api.DeploymentInfo;

@Configuration
public class JspAutoConfiguration {

	/**
	 * Cria JspServlet, configura e registra no Undertow
	 * @author hirata
	 *
	 */
	public static class JspForUndertowContainerCustomizer extends WebSocketContainerCustomizer<UndertowEmbeddedServletContainerFactory> {

		@Override
		protected void doCustomize(UndertowEmbeddedServletContainerFactory container) {
			container.addDeploymentInfoCustomizers(new UndertowDeploymentInfoCustomizer() {

				@Override
				public void customize(DeploymentInfo deploymentInfo) {
					deploymentInfo.addServlet(JspServletBuilder.createServlet("Default JSP Servlet", "*.jsp"));

					HashMap<String, TagLibraryInfo> tagLibraryInfo;
					try {
						tagLibraryInfo = TldLocator.createTldInfos();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}

					JspServletBuilder.setupDeployment(deploymentInfo, new HashMap<String, JspPropertyGroup>(),
							tagLibraryInfo, new HackInstanceManager());
				}
			});
			
		}
	}
	
	@Bean
	public JspForUndertowContainerCustomizer jspForUndertowContainerCustomizer() {
		return new JspForUndertowContainerCustomizer();
	}
	
	@Bean
	public InternalResourceViewResolver jstlViewResolver() {
		InternalResourceViewResolver v = new  InternalResourceViewResolver("/WEB-INF/jsp/", ".jsp");
		v.setOrder(Ordered.LOWEST_PRECEDENCE - 6);
		return v;
	}
}
