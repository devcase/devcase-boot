package br.com.devcase.boot.social.azure.autoconfigure;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.GenericConnectionStatusView;
import org.springframework.social.oauth2.GenericOAuth2ConnectionFactory;
import org.springframework.social.oauth2.TokenStrategy;

import br.com.devcase.boot.social.azure.connect.MicrosoftGraphAdapter;

@Configuration
@ConditionalOnClass({ SocialConfigurerAdapter.class })
@ConditionalOnProperty(prefix = "devcase.social.azure-ad", name = "app-id")
@AutoConfigureBefore(SocialWebAutoConfiguration.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class AzureADSocialAutoConfiguration {

	@Configuration
	@EnableSocial
	@ConditionalOnWebApplication(type = Type.SERVLET)
	@EnableConfigurationProperties(AzureADProperties.class)
	protected static class AzureADConfigurerAdapter extends SocialAutoConfigurerAdapter {
		@Autowired
		private AzureADProperties azureADProperties;

		@Override
		protected ConnectionFactory<?> createConnectionFactory() {
			GenericOAuth2ConnectionFactory connectionFactory = new GenericOAuth2ConnectionFactory(
					"azure-ad",
					azureADProperties.getAppId(), 
					azureADProperties.getAppSecret(), 
					MessageFormat.format("https://login.microsoftonline.com/{0}/oauth2/v2.0/authorize", azureADProperties.getTenant()), 
					MessageFormat.format("https://login.microsoftonline.com/{0}/oauth2/v2.0/authorize", azureADProperties.getTenant()), 
					MessageFormat.format("https://login.microsoftonline.com/{0}/oauth2/v2.0/token", azureADProperties.getTenant()), 
					true,
					TokenStrategy.AUTHORIZATION_HEADER,
					new MicrosoftGraphAdapter() 
					);
			return connectionFactory;
		}
		

		@Bean(name = { "connect/azure-adConnect", "connect/azure-adConnected" })
		@ConditionalOnProperty(prefix = "spring.social", name = "auto-connection-views")
		public GenericConnectionStatusView facebookConnectView() {
			return new GenericConnectionStatusView("azure-ad", "Azure AD");
		}

	}

}
