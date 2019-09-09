package com.me.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {
	@Bean("clientPasswordEncoder")
	public PasswordEncoder clientPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean("userPasswordEncoder")
	public PasswordEncoder userPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
