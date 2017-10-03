package br.com.devcase.boot.users.webadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.devcase.boot.users.domain.entities.PasswordCredential;
import br.com.devcase.boot.users.domain.entities.User;
import br.com.devcase.boot.users.domain.entities.UserPermission;
import br.com.devcase.boot.users.domain.entities.oauth2.OAuth2Client;
import br.com.devcase.boot.users.security.config.CommonSecurityConfig;
import br.com.devcase.boot.users.webadmin.repositories.CredentialRepository;
import br.com.devcase.boot.users.webadmin.repositories.UserPermissionRepository;
import br.com.devcase.boot.users.webadmin.repositories.UserRepository;
import br.com.devcase.boot.users.webadmin.repositories.oauth2.OAuth2ClientRepository;

@Configuration
@Import(CommonSecurityConfig.class)
@Profile("demo")
public class DemoConfig {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CredentialRepository credentialRepository;
	@Autowired
	private UserPermissionRepository userPermissionRepository;
	@Autowired
	private OAuth2ClientRepository oAuth2ClientRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@EventListener(ApplicationReadyEvent.class)
	public void loadRootUser() {
		final String password = "root";
		final String login = "root";
		if(userRepository.countByName(login) == 0) {
			User user1 = new User();
			user1.setName(login);
			userRepository.save(user1);
			PasswordCredential credential = new PasswordCredential();
			credential.setUser(user1);
			credential.setPassword(passwordEncoder.encode(password));
			credentialRepository.save(credential);
			userPermissionRepository.save(UserPermission.builder().withRole("USER").withUser(user1).build());
			userPermissionRepository.save(UserPermission.builder().withRole("ACTUATOR").withUser(user1).build());
			userPermissionRepository.save(UserPermission.builder().withRole("SUPERUSER").withUser(user1).build());
		}
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void loadGuestUser() {
		final String password = "guest";
		final String login = "guest";
		if(userRepository.countByName(login) == 0) {
			User user1 = new User();
			user1.setName(login);
//			user1.setRoles(Lists.newArrayList("ROLE_USER"));
			userRepository.save(user1);
			PasswordCredential credential = new PasswordCredential();
			credential.setUser(user1);
			credential.setPassword(passwordEncoder.encode(password));
			credentialRepository.save(credential);
			userPermissionRepository.save(UserPermission.builder().withRole("USER").withUser(user1).build());
		}
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void loadDevClient() {
		final String login = "ASKdBkpwYwtZhsur1HEUP9QJK2wg6FqC";
		final String password = "E4KLddpituzFF6vDqA2ohHFRLJgTRfxx";
		if(oAuth2ClientRepository.countByClientId(login) == 0) {
			oAuth2ClientRepository.save(
					OAuth2Client.builder()
					.withClientSecret(password)
					.withAccessTokenValidity(3600)
					.withRefreshTokenValidity(3600)
					.withName("DEV")
					.withAuthorizedGrantTypes("authorization_code,refresh_token")
					.withScope("openid,email,profile")
					.withClientId(login).build());
		}
	}

}
