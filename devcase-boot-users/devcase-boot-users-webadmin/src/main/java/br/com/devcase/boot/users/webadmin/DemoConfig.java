package br.com.devcase.boot.users.webadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import br.com.devcase.boot.users.domain.entities.PasswordCredential;
import br.com.devcase.boot.users.domain.entities.User;
import br.com.devcase.boot.users.domain.repositories.CredentialRepository;
import br.com.devcase.boot.users.domain.repositories.UserRepository;

@Component
@Profile("demo")
public class DemoConfig {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CredentialRepository credentialRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@EventListener(ApplicationReadyEvent.class)
	public void loadRootUser() {
		final String password = "root";
		final String login = "root";
		if(userRepository.countByName(login) == 0) {
			User user1 = new User();
			user1.setName(login);
			user1.setRoles(Lists.newArrayList("ROLE_USER", "ROLE_SUPERUSER"));
			userRepository.save(user1);
			PasswordCredential credential = new PasswordCredential();
			credential.setUser(user1);
			credential.setPassword(passwordEncoder.encode(password));
			credentialRepository.save(credential);
		}
	}
}
