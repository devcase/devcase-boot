package br.com.devcase.boot.users.security.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@ConditionalOnWebApplication
@EnableWebSecurity
public class DefaultWebSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {

}
