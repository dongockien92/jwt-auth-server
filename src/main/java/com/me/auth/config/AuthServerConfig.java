package com.me.auth.config;

import java.security.KeyPair;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.me.auth.properties.JwtProperties;
import com.me.auth.properties.SecurityProperties;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private DataSource ds;

	@Autowired
	private AuthenticationManager authMgr;

	@Autowired
	private UserDetailsService usrSvc;

	@Autowired
	private SecurityProperties securityProperties;

	@Resource(name = "clientPasswordEncoder")
	private PasswordEncoder clientPasswordEncoder;

	private KeyStoreKeyFactory keyStoreKeyFactory(JwtProperties jwtProperties) {
		return new KeyStoreKeyFactory(jwtProperties.getKeyStore(), jwtProperties.getKeyStorePassword().toCharArray());
	}

	private KeyPair keyPair(JwtProperties jwtProperties, KeyStoreKeyFactory keyStoreKeyFactory) {
		return keyStoreKeyFactory.getKeyPair(jwtProperties.getKeyAlias(), jwtProperties.getKeyPassword().toCharArray());
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtProperties jwtProperties = securityProperties.getJwt();
		KeyPair keyPair = keyPair(jwtProperties, keyStoreKeyFactory(jwtProperties));

		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setKeyPair(keyPair);
		return jwtAccessTokenConverter;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer cfg) throws Exception {

		// Enable /oauth/token_key URL used by resource server to validate JWT tokens
		cfg.tokenKeyAccess("permitAll");

		// Enable /oauth/check_token URL
		cfg.checkTokenAccess("permitAll");

		// BCryptPasswordEncoder() is used for oauth_client_details.user_secret
		cfg.passwordEncoder(clientPasswordEncoder);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(ds);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.accessTokenConverter(jwtAccessTokenConverter());
		endpoints.authenticationManager(authMgr);
		endpoints.userDetailsService(usrSvc);
	}
}
