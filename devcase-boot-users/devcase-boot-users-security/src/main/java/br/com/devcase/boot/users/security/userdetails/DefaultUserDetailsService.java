package br.com.devcase.boot.users.security.userdetails;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.google.common.collect.Lists;

import br.com.devcase.boot.users.domain.entities.GroupPermission;
import br.com.devcase.boot.users.domain.entities.PasswordCredential;
import br.com.devcase.boot.users.domain.entities.Permission;
import br.com.devcase.boot.users.domain.entities.User;
import br.com.devcase.boot.users.domain.entities.UserPermission;
import br.com.devcase.boot.users.repositories.CredentialRepository;
import br.com.devcase.boot.users.repositories.GroupPermissionRepository;
import br.com.devcase.boot.users.repositories.UserPermissionRepository;
import br.com.devcase.boot.users.repositories.UserRepository;

public class DefaultUserDetailsService implements UserDetailsService {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	UserRepository userRepository;
	@Autowired
	CredentialRepository credentialRepository;
	@Autowired
	UserPermissionRepository userPermissionReadOnlyRepository;
	@Autowired
	GroupPermissionRepository groupPermissionReadOnlyRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		PasswordCredential passCred = credentialRepository.findPasswordCredentialByUser(user);
		List<UserPermission> userPermissions = userPermissionReadOnlyRepository.findValidByUser(user);
		List<GroupPermission> groupPermissions = groupPermissionReadOnlyRepository.findValidByUser(user);
		ArrayList<Permission> permissions = Lists.newArrayList(userPermissions);
		permissions.addAll(groupPermissions);
		
		return new DefaultUserDetails(user, passCred, permissions);
	}
	//
	// @Override
	// public boolean userExists(String username) {
	// logger.debug("userExists");
	// return userRepository.countByName(username) > 0;
	// }
	//
	// @Override
	// public void updateUser(UserDetails user) {
	// // noop
	//
	// }
	//
	// @Override
	// public void deleteUser(String username) {
	// User user = userRepository.findByName(username);
	// userRepository.delete(user);
	// }
	//
	// @Override
	// public void createUser(UserDetails userDetails) {
	// User user = new User();
	// user.setName(userDetails.getUsername());
	// List<String> roles = userDetails.getAuthorities().stream().map(auth ->
	// auth.getAuthority())
	// .collect(Collectors.toList());
	// user.setRoles(roles);
	// userRepository.save(user);
	// PasswordCredential passwordCredential = new PasswordCredential();
	// passwordCredential.setUser(user);
	// passwordCredential.setPassword(userDetails.getPassword());
	// credentialRepository.save(passwordCredential);
	// }
	//
	// @Override
	// public void changePassword(String oldPassword, String newPassword) {
	// Authentication currentUser =
	// SecurityContextHolder.getContext().getAuthentication();
	//
	// if (currentUser == null) {
	// throw new AccessDeniedException(
	// "Can't change password as no Authentication object found in context "
	// + "for current user.");
	// }
	//
	// String username = currentUser.getName();
	// PasswordCredential cred =
	// credentialRepository.findPasswordCredentialByUsername(username);
	// if (cred == null) {
	// throw new AccessDeniedException(
	// "Can't change password as no Authentication object found in context "
	// + "for current user.");
	// }
	//
	// if(oldPassword != null && oldPassword.equals(cred.getPassword())) {
	// cred.setPassword(newPassword);
	// credentialRepository.save(cred);
	// }
	// }
}
