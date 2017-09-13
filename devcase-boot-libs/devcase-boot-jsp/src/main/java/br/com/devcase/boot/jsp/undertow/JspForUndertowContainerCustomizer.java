package br.com.devcase.boot.jsp.undertow;

import java.io.IOException;
import java.util.HashMap;

import org.apache.jasper.deploy.JspPropertyGroup;
import org.apache.jasper.deploy.TagLibraryInfo;
import org.springframework.boot.autoconfigure.websocket.WebSocketContainerCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;

import io.undertow.jsp.HackInstanceManager;
import io.undertow.jsp.JspServletBuilder;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.api.DeploymentInfo;

/**
 * Registra JspServlet
 * @author hirata
 *
 */
public class JspForUndertowContainerCustomizer
		extends WebSocketContainerCustomizer<UndertowEmbeddedServletContainerFactory> {

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
				
				
//				deploymentInfo.setResourceManager(new ClassPathResourceManager(Thread.currentThread().getContextClassLoader()));
			}
		});

	}
}