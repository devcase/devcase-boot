package br.com.devcase.boot.jsp.undertow;

import java.io.IOException;
import java.util.HashMap;

import org.apache.jasper.deploy.JspPropertyGroup;
import org.apache.jasper.deploy.TagLibraryInfo;
import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

import io.undertow.Undertow;
import io.undertow.jsp.HackInstanceManager;
import io.undertow.jsp.JspServletBuilder;
import io.undertow.servlet.api.DeploymentInfo;

/**
 * Registra JspServlet
 * @author hirata
 *
 */
public class JspForUndertowContainerCustomizer
		implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

	
	@Override
	public void customize(UndertowServletWebServerFactory server) {
		server.addDeploymentInfoCustomizers(new UndertowDeploymentInfoCustomizer() {

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