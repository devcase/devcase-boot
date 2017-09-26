package br.com.devcase.boot.users.security.userdetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.devcase.boot.users.domain.entities.PasswordCredential;
import br.com.devcase.boot.users.domain.entities.Permission;

public class DefaultUserDetails extends User {

	public DefaultUserDetails(br.com.devcase.boot.users.domain.entities.User user,
			PasswordCredential passwordCredential, List<? extends Permission> permissions) {
		super(user.getName(), passwordCredential != null ? passwordCredential.getPassword() : null, user.isEnabled(),
				!user.isExpired(), passwordCredential != null ? !passwordCredential.isExpired() : true,
				!user.isLocked(), convertToAuthorities(permissions));
	}

	private static Collection<? extends GrantedAuthority> convertToAuthorities(List<? extends Permission> roles) {
		return roles.stream().map(s -> new SimpleGrantedAuthority("ROLE_".concat(s.getRole())))
				.distinct().collect(Collectors.toList());
	}

}
