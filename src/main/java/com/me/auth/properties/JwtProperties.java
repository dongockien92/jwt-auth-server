package com.me.auth.properties;

import org.springframework.core.io.Resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtProperties {
	private Resource keyStore;
	private String keyStorePassword;
	private String keyAlias;
	private String keyPassword;
}
