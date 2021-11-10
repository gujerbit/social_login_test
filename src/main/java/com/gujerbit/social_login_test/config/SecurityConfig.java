package com.gujerbit.social_login_test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.gujerbit.social_login_test.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomOAuth2UserService service;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login", "/css/**", "/img/**", "/js/**").permitAll().anyRequest().authenticated(); //antMatchers.permitAll no need permission, anyRequest.authenticated need permission
		http.oauth2Login().loginPage("/login").userInfoEndpoint().userService(service); //if login success this service execute
	}
	
}
