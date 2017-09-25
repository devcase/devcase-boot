package br.com.devcase.boot.users.domain.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import br.com.devcase.boot.users.domain.DevcaseUsersConfig;

@Configuration
@Import(DevcaseUsersConfig.class)
public class DevcaseUsersAutoConfiguration {

}
