package br.com.devcase.boot.users.security.userdetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
//import org.springframework.social.security.SocialUserDetails;

import br.com.devcase.boot.users.domain.entities.PasswordCredential;
import br.com.devcase.boot.users.domain.entities.Permission;

public class DefaultUserDetails extends User {

	private static final long serialVersionUID = 6732550151456123873L;

	private String name;
	private String email;

	public DefaultUserDetails(br.com.devcase.boot.users.domain.entities.User user,
			PasswordCredential passwordCredential, List<? extends Permission> permissions) {
		super(user.getUsername(), passwordCredential != null ? passwordCredential.getPassword() : "", user.isEnabled(),
				!user.isExpired(), passwordCredential != null ? !passwordCredential.isExpired() : true,
				!user.isLocked(), convertToAuthorities(permissions));

		name = user.getName();
		email = user.getUsername();

		if (passwordCredential == null) {
			this.eraseCredentials();
		}
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	private static Collection<? extends GrantedAuthority> convertToAuthorities(List<? extends Permission> roles) {
		return roles.stream().map(s -> new SimpleGrantedAuthority("ROLE_".concat(s.getRole()))).distinct()
				.collect(Collectors.toList());
	}

}
