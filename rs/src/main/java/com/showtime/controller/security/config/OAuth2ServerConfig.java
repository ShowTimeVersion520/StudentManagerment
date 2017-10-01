/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.showtime.controller.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

/**
 * @author Rob Winch
 * 
 */
@Configuration
public class OAuth2ServerConfig {

	private static final String RESOURCE_ID = "SHOWTIME_RESOURCE";

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends
			ResourceServerConfigurerAdapter {

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources.resourceId(RESOURCE_ID).stateless(false);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				// Since we want the protected resources to be accessible in the UI as well we need 
				// session creation to be allowed (it's disabled by default in 2.0.6)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			.and()
				.anonymous().disable()
				.requestMatchers().antMatchers("/api/**")
			.and()
				.authorizeRequests()
                    .antMatchers("/api/**").access("#oauth2.hasScope('read') or hasRole('ROLE_ADMIN')")
			.and()
				.exceptionHandling()
					.accessDeniedHandler(new OAuth2AccessDeniedHandler());
			// @formatter:on
		}

	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends
			AuthorizationServerConfigurerAdapter {

		private static String REALM="SHOWTIME_OAUTH_REALM";

		@Autowired
		private DataSource dataSource;

		@Autowired
		private TokenStore tokenStore;

		@Autowired
		private UserApprovalHandler userApprovalHandler;


		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;

		@Value("${tonr.redirect:http://localhost:8080/tonr2/sparklr/redirect}")
		private String tonrRedirectUri;



		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			// @formatter:off
			clients.inMemory()
                        .withClient("my-trusted-client").secret("secret")
                            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                            .authorities("ROLE_USER", "ROLE_ADMIN")
                            .scopes("read", "write", "trust")
                            .accessTokenValiditySeconds(7200)
                            .refreshTokenValiditySeconds(10800)
                            .redirectUris("http://localhost:8080/api/v1/sysusers");
			// @formatter:on
		}


		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.tokenStore(tokenStore)
                    .userApprovalHandler(userApprovalHandler)
					.authenticationManager(authenticationManager);
		}

		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
			oauthServer.realm(REALM + "/client");
		}



	}

//	protected static class Stuff{
//
//		@Autowired
//		private DataSource dataSource;
//
//		@Autowired
//		private TokenStore tokenStore;
//
//
//		@Autowired
//		private ClientDetailsService clientDetailsService;
//
//		@Bean
//		public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
//			TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
//			handler.setTokenStore(tokenStore);
//			handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
//			handler.setClientDetailsService(clientDetailsService);
//			return handler;
//		}
//
//		@Bean
//		public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
//			TokenApprovalStore store = new TokenApprovalStore();
//			store.setTokenStore(tokenStore);
//			return store;
//		}
//	}

//	protected static class Stuff {
//
//		@Autowired
//		private ClientDetailsService clientDetailsService;
//
//		@Autowired
//		private TokenStore tokenStore;
//
//        @Bean
//		public ApprovalStore approvalStore() throws Exception {
//			TokenApprovalStore store = new TokenApprovalStore();
//			store.setTokenStore(tokenStore);
//            return store;
//		}
//
//		@Bean
//		@Lazy
//		@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
//		public SparklrUserApprovalHandler userApprovalHandler() throws Exception {
//			SparklrUserApprovalHandler handler = new SparklrUserApprovalHandler();
//			handler.setApprovalStore(approvalStore());
//			handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
//			handler.setClientDetailsService(clientDetailsService);
//			handler.setUseApprovalStore(true);
//			return handler;
//		}
//	}

}
