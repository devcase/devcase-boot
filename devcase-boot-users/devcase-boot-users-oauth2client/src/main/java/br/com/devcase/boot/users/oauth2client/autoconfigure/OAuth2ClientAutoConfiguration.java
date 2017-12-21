package br.com.devcase.boot.users.oauth2client.autoconfigure;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;


/**
 * Procura por registro de cliente chamado default
 * @author hirata
 *
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(OAuth2ClientProperties.class)
@ConditionalOnProperty(prefix="spring.security.oauth2.client.registration.default", name="client-id")
public class OAuth2ClientAutoConfiguration {
	private final OAuth2ClientProperties properties;

	public OAuth2ClientAutoConfiguration(OAuth2ClientProperties properties) {
		super();
		this.properties = properties;
	}
	
	@Bean
	public ClientRegistrationRepository defaultClientRegistrationRepository () {
		OAuth2ClientProperties.Registration registration = properties.getRegistration().get("default");
		OAuth2ClientProperties.Provider provider = properties.getProvider().get(registration.getProvider());
		ClientRegistration.Builder builder = ClientRegistration.withRegistrationId("default");
		copyIfNotNull(registration::getClientId, builder::clientId);
		copyIfNotNull(registration::getClientSecret, builder::clientSecret);
		copyIfNotNull(registration::getClientAuthenticationMethod,
				builder::clientAuthenticationMethod, ClientAuthenticationMethod::new);
		copyIfNotNull(registration::getAuthorizationGrantType,
				builder::authorizationGrantType, AuthorizationGrantType::new);
		copyIfNotNull(registration::getRedirectUriTemplate, builder::redirectUriTemplate);
		copyIfNotNull(registration::getScope, builder::scope,
				(scope) -> scope.toArray(new String[scope.size()]));
		copyIfNotNull(registration::getClientName, builder::clientName);
		copyIfNotNull(provider::getAuthorizationUri, builder::authorizationUri);
		copyIfNotNull(provider::getTokenUri, builder::tokenUri);
		copyIfNotNull(provider::getUserInfoUri, builder::userInfoUri);
		copyIfNotNull(provider::getJwkSetUri, builder::jwkSetUri);
		copyIfNotNull(provider::getUserNameAttribute, builder::userNameAttributeName);
		
		ClientRegistration clientRegistration = builder.build();
		return new ClientRegistrationRepository() {
			@Override
			public ClientRegistration findByRegistrationId(String registrationId) {
				if(registrationId.equals("default"))
					return clientRegistration;
				return null;
			}
		};
	}

	private static <T> void copyIfNotNull(Supplier<T> supplier, Consumer<T> consumer) {
		copyIfNotNull(supplier, consumer, Function.identity());
	}

	private static <S, C> void copyIfNotNull(Supplier<S> supplier, Consumer<C> consumer,
			Function<S, C> converter) {
		S value = supplier.get();
		if (value != null) {
			consumer.accept(converter.apply(value));
		}
	}


	@Order(0)
	@Configuration
	static class OAuth2ClientWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

		/**
		 * Check {@link org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientRegistrationRepositoryConfiguration}
		 */
		@Autowired
		ClientRegistrationRepository clientRegistrationRepository;

		
		public OAuth2ClientWebSecurityConfigurer() {
			super(true);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.csrf().and()
				.addFilter(new WebAsyncManagerIntegrationFilter())
				.exceptionHandling().and()
				.headers().and()
				.sessionManagement().and()
				.securityContext().and()
				.requestCache().and()
				.anonymous().and()
				.servletApi().and()
				.formLogin().loginPage("/oauth2/authorization/code/default").and()
				.authorizeRequests().anyRequest().authenticated().and()
				.oauth2Login().clientRegistrationRepository(clientRegistrationRepository).and()
				.logout();
		}
	}
}
