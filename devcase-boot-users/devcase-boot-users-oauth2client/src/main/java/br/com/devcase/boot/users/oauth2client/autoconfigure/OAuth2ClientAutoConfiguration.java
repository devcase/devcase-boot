package br.com.devcase.boot.users.oauth2client.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
@PropertySource(value="classpath:/devcase-provider.properties")
public class OAuth2ClientAutoConfiguration {

}
