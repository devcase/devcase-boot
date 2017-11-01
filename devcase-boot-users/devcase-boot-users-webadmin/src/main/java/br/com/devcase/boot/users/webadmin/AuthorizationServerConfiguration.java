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
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;

import br.com.devcase.boot.users.domain.entities.User;
import br.com.devcase.boot.users.repositories.UserRepository;

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

		@Autowired
		private UserRepository userRepository;
		
		@RequestMapping("/userinfo")
		public ResponseEntity<Map<String, Object>> userInfo() {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}

			String username = authentication.getPrincipal() instanceof String ? (String) authentication.getPrincipal()
					: authentication.getDetails() instanceof String ? (String) authentication.getDetails()
							: authentication.getDetails() instanceof UserDetails
									? ((UserDetails) authentication.getDetails()).getUsername()
									: null;

			Map<String, Object> claims = Maps.newHashMap();
			claims.put("username", username);
			if (username != null) {
				User user = userRepository.findByUsername(username);
				claims.put("sub", user.getId());
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
			http.requestMatchers().antMatchers("/userinfo").and().authorizeRequests().anyRequest().permitAll().and()
					.exceptionHandling().accessDeniedHandler(resources.getAccessDeniedHandler()).and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable()
					.apply(resources);
			// @formatter:on
		}

	}

}
