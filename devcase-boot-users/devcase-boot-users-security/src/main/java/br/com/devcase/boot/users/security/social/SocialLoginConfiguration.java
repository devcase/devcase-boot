package br.com.devcase.boot.users.security.social;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import com.google.common.collect.Lists;

import br.com.devcase.boot.users.domain.entities.GroupPermission;
import br.com.devcase.boot.users.domain.entities.PasswordCredential;
import br.com.devcase.boot.users.domain.entities.Permission;
import br.com.devcase.boot.users.domain.entities.User;
import br.com.devcase.boot.users.domain.entities.UserPermission;
import br.com.devcase.boot.users.security.config.WebFormAuthenticationConfig;
import br.com.devcase.boot.users.security.repositories.CredentialReadOnlyRepository;
import br.com.devcase.boot.users.security.repositories.GroupPermissionsReadOnlyRepository;
import br.com.devcase.boot.users.security.repositories.UserPermissionsReadOnlyRepository;
import br.com.devcase.boot.users.security.repositories.UserReadOnlyRepository;

@Configuration
@EnableSocial
@Import(WebFormAuthenticationConfig.class)
public class SocialLoginConfiguration {

	@Bean
	public UsersConnectionRepository usersConnectionRepository() {
		return new UsersConnectionRepositoryJpaDataImpl();
	}

	@Order(WebFormAuthenticationConfig.WEBFORM_SECURITY_ORDER + 1)
	@Configuration
	public static class SocialWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

		public SocialWebSecurityConfigurer() {
			super(false);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
					.and().logout().permitAll().and().apply(new SpringSocialConfigurer());
		}

		@Override
		public void init(WebSecurity web) throws Exception {
			super.init(web);
			web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/**/favicon.ico");
		}

	}

	@Component
	public static class SocialSignInAdapter implements SignInAdapter {

		@Override
		public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
			
			System.out.println("SIGNIN!!!!!!!!!!!!");
			SecurityContextHolder.getContext()
					.setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, null));
			return null;
		}
	}
	
	@Component
	@ConditionalOnMissingBean(value=TextEncryptor.class)
	public static class AccessCodeEncryptor implements TextEncryptor  {

		@Override
		public String encrypt(String text) {
			return text;
		}

		@Override
		public String decrypt(String encryptedText) {
			return encryptedText;
		}
		
	}
	

	@Component
	public static class SocialDetailsService implements SocialUserDetailsService {
		@Autowired
		UserReadOnlyRepository userRepository;
		@Autowired
		CredentialReadOnlyRepository credentialRepository;
		@Autowired
		UserPermissionsReadOnlyRepository userPermissionsReadOnlyRepository;
		@Autowired
		GroupPermissionsReadOnlyRepository groupPermissionsReadOnlyRepository;

		@Override
		public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
			Optional<User> u = userRepository.findById(userId);
			if (!u.isPresent()) {
				throw new UsernameNotFoundException("User not found");
			}
			User user = u.get();
			PasswordCredential passCred = credentialRepository.findPasswordCredentialByUser(user);
			List<UserPermission> userPermissions = userPermissionsReadOnlyRepository.findValidByUser(user);
			List<GroupPermission> groupPermissions = groupPermissionsReadOnlyRepository.findValidByUser(user);
			ArrayList<Permission> permissions = Lists.newArrayList(userPermissions);
			permissions.addAll(groupPermissions);

			return new SocialUser(user.getName(), passCred != null ? passCred.getPassword() : null, user.isEnabled(),
					!user.isExpired(), passCred != null ? !passCred.isExpired() : true, !user.isLocked(),
					convertToAuthorities(permissions));
		}

		private static Collection<? extends GrantedAuthority> convertToAuthorities(List<? extends Permission> roles) {
			return roles.stream().map(s -> new SimpleGrantedAuthority("ROLE_".concat(s.getRole()))).distinct()
					.collect(Collectors.toList());
		}

	}

}
