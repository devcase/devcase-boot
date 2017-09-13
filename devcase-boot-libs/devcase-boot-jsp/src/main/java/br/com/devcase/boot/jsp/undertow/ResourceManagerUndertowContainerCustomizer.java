package br.com.devcase.boot.jsp.undertow;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.websocket.WebSocketContainerCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;

import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.Resource;
import io.undertow.server.handlers.resource.ResourceChangeListener;
import io.undertow.server.handlers.resource.ResourceManager;
import io.undertow.server.handlers.resource.URLResource;
import io.undertow.servlet.api.DeploymentInfo;

/**
 * Configura quais os resources disponíveis para o Undertow, para ele conseguir
 * carregar tagfiles de dependências a partir do ClassPath
 * 
 * @author hirata
 *
 */
public class ResourceManagerUndertowContainerCustomizer
		extends WebSocketContainerCustomizer<UndertowEmbeddedServletContainerFactory> {

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	protected void doCustomize(UndertowEmbeddedServletContainerFactory container) {
		container.addDeploymentInfoCustomizers(new UndertowDeploymentInfoCustomizer() {
			@Override
			public void customize(DeploymentInfo deploymentInfo) {
				deploymentInfo.setResourceManager(getCustomResourceManager(deploymentInfo.getResourceManager()));
			}
		});
	}

	protected ResourceManager getCustomResourceManager(final ResourceManager originalResourceManager) {
		return new ResourceManager() {

			@Override
			public void close() throws IOException {
				originalResourceManager.close();
			}

			@Override
			public void removeResourceChangeListener(ResourceChangeListener listener) {
				originalResourceManager.removeResourceChangeListener(listener);
			}

			@Override
			public void registerResourceChangeListener(ResourceChangeListener listener) {
				originalResourceManager.registerResourceChangeListener(listener);
			}

			@Override
			public boolean isResourceChangeListenerSupported() {
				return originalResourceManager.isResourceChangeListenerSupported();
			}

			@Override
			public Resource getResource(String path) throws IOException {

				if (path.startsWith("/META-INF/tags") && path.endsWith(".tag")
						|| path.startsWith("/META-INF/") && path.endsWith(".tld")) {
					final URLClassLoader loader = (URLClassLoader) applicationContext.getClassLoader();
					URL url = loader.getResource(path.substring(1));
					if (url != null) {
						return new URLResource(url, path);
					}
				}
				return originalResourceManager.getResource(path);
			}
		};
	}

}
