package br.com.devcase.boot.users.webadmin;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration {

	@Configuration
	public static class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

		@Autowired
		DataSource dataSource;
		@Autowired
		PasswordEncoder passwordEncoder;

		@Bean
		public JwtAccessTokenConverter accessTokenConverter() {
			return new JwtAccessTokenConverter();
		}

		@Bean
		public JwtTokenStore jwtTokenStore() {
			return new JwtTokenStore(accessTokenConverter());
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.accessTokenConverter(accessTokenConverter()).tokenEnhancer(accessTokenConverter())
					.tokenStore(jwtTokenStore());
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
		}

		@Override
		public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
			security.checkTokenAccess("isAuthenticated()").tokenKeyAccess("permitAll()");
		}
	}

	@FrameworkEndpoint
	public static class UserInfoController {

		@RequestMapping("/userinfo")
		public ResponseEntity<Map<String, Object>> userInfo() {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}

			Object authenticationDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			UserDetails userDetails = authenticationDetails instanceof UserDetails ? (UserDetails) authenticationDetails
					: null;
			
			SocialUserDetails defaultUserDetails = authenticationDetails instanceof SocialUserDetails ? (SocialUserDetails) authenticationDetails : null;

			Map<String, Object> claims = Maps.newHashMap();
			if(userDetails != null) {
				claims.put("sub", defaultUserDetails != null ?  defaultUserDetails.getUserId() : userDetails.getUsername());
				claims.put("username", userDetails.getUsername());
			}
			return new ResponseEntity<>(claims, HttpStatus.OK);
		}
	}
	
	@Order(4)
	@Configuration
	public static class UserInfoSecurityConfigurer extends WebSecurityConfigurerAdapter {
		@Autowired
		private TokenStore tokenStore;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			ResourceServerSecurityConfigurer resources = new ResourceServerSecurityConfigurer();
			if (tokenStore != null) {
				resources.tokenStore(tokenStore);
			}
			
			// @formatter:off
			http.requestMatchers().antMatchers("/userinfo").and()
				.authorizeRequests().anyRequest().permitAll().and()
				.exceptionHandling().accessDeniedHandler(resources.getAccessDeniedHandler()).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.csrf().disable()
				.apply(resources);
			// @formatter:on
		}
		
		
	}
	
}
