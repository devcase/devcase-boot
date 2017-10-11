package br.com.devcase.boot.users.security.social;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.SignInAdapter;
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
import br.com.devcase.boot.users.security.userdetails.DefaultUserDetails;
import br.com.devcase.boot.users.security.userdetails.DefaultUserDetailsService;

@Configuration
@EnableSocial
@Import(WebFormAuthenticationConfig.class)
public class SocialLoginConfiguration {

	@Bean
	public UsersConnectionRepository usersConnectionRepository() {
		return new UsersConnectionRepositoryJpaDataImpl();
	}

	@Order(WebFormAuthenticationConfig.WEBFORM_SECURITY_ORDER)
	@Configuration
	public static class SocialWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/**/favicon.ico");
		}


		@Override
		protected void configure(HttpSecurity http) throws Exception {
			logger.debug("Configuring http for social ");
			http.authorizeRequests()
				.anyRequest().authenticated().and()
				.formLogin().loginPage("/login").permitAll().and()
				.logout().permitAll().and()
				.apply(new SpringSocialConfigurer());
		}
	}

	@Component
	public static class SocialSignInAdapter implements SignInAdapter {

		@Override
		public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
			
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
	@ConditionalOnBean(DefaultUserDetailsService.class)
	public static class SocialLoginUserSource implements UserIdSource {

		@Override
		public String getUserId() {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null) {
				throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
			}
			if(authentication.getDetails() != null && authentication.getDetails() instanceof DefaultUserDetails) {
				return ((DefaultUserDetails) authentication.getDetails()).getUserId();
			}
			return authentication.getName();		
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

			return new DefaultUserDetails(user, passCred, permissions);
		}

	}

}
