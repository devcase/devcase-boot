package br.com.devcase.boot.users.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.devcase.boot.users.domain.entities.PasswordCredential;

public class DefaultUserDetails extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DefaultUserDetails(br.com.devcase.boot.users.domain.entities.User user, PasswordCredential passwordCredential) {
		super(user.getName(), passwordCredential != null ? passwordCredential.getPassword() : null, user.isEnabled(), !user.isExpired(), passwordCredential != null ? !passwordCredential.isExpired() : true, !user.isLocked(), convertToAuthorities(user.getRoles()));
	}

	private static Collection<? extends GrantedAuthority> convertToAuthorities(List<String> roles) {
		return roles.stream().map(s -> new SimpleGrantedAuthority(s.toUpperCase())).collect(Collectors.toList());
	}

}
