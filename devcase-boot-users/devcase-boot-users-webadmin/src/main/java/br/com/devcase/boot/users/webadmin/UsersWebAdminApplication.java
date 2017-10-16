package br.com.devcase.boot.users.webadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import br.com.devcase.boot.users.security.social.EnableSocialLogin;

@SpringBootApplication
//@EnableWebFormAuthentication
@EnableSocialLogin
@Import(UsersWebAdminConfiguration.class)
public class UsersWebAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersWebAdminApplication.class, args);
	}

	
}
