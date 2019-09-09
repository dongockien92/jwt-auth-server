package com.me.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("security.oauth2.authorization")
@Getter
@Setter
@Component
public class SecurityProperties {
	private JwtProperties jwt;
}
