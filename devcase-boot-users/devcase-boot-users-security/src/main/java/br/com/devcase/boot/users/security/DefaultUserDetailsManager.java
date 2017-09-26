package br.com.devcase.boot.users.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import br.com.devcase.boot.users.domain.entities.PasswordCredential;
import br.com.devcase.boot.users.domain.entities.User;
import br.com.devcase.boot.users.domain.repositories.CredentialRepository;
import br.com.devcase.boot.users.domain.repositories.UserRepository;

public class DefaultUserDetailsManager implements UserDetailsManager {
	@Autowired
	UserRepository userRepository;
	@Autowired
	CredentialRepository credentialRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByName(username);
		PasswordCredential passCred = credentialRepository.findPasswordCredentialByUser(user);
		return new DefaultUserDetails(user, passCred);
	}

	@Override
	public boolean userExists(String username) {
		return userRepository.countByName(username) > 0;
	}

	@Override
	public void updateUser(UserDetails user) {
		// noop

	}

	@Override
	public void deleteUser(String username) {
		User user = userRepository.findByName(username);
		userRepository.delete(user);
	}

	@Override
	public void createUser(UserDetails userDetails) {
		User user = new User();
		user.setName(userDetails.getUsername());
		List<String> roles = userDetails.getAuthorities().stream().map(auth -> auth.getAuthority())
				.collect(Collectors.toList());
		user.setRoles(roles);
		userRepository.save(user);
		PasswordCredential passwordCredential = new PasswordCredential();
		passwordCredential.setUser(user);
		passwordCredential.setPassword(userDetails.getPassword());
		credentialRepository.save(passwordCredential);
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

		if (currentUser == null) {
			throw new AccessDeniedException(
					"Can't change password as no Authentication object found in context "
							+ "for current user.");
		}

		String username = currentUser.getName();
		PasswordCredential cred = credentialRepository.findPasswordCredentialByUsername(username);
		if (cred == null) {
			throw new AccessDeniedException(
					"Can't change password as no Authentication object found in context "
							+ "for current user.");
		}
		
		if(oldPassword != null && oldPassword.equals(cred.getPassword())) {
			cred.setPassword(newPassword);
			credentialRepository.save(cred);
		}
	}
}
