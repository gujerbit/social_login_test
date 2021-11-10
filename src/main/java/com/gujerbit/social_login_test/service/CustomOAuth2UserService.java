package com.gujerbit.social_login_test.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.gujerbit.social_login_test.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	
	private final HttpSession session;
	private User vo = new User();

	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws org.springframework.security.oauth2.core.OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService(); //initialize
		OAuth2User user = delegate.loadUser(userRequest); //load user info
		String id = userRequest.getClientRegistration().getRegistrationId(); //provider id
		System.out.println(id);
		for(String key : user.getAttributes().keySet()) System.out.println(key + " : " + user.getAttribute(key));
		
		if(id.equals("google")) {
			vo.setName(user.getAttribute("name"));
			vo.setEmail(user.getAttribute("email"));
		} else if(id.equals("naver")) {
			Map<String, String> map = user.getAttribute("response");
			vo.setName(map.get("name"));
			vo.setEmail(map.get("email"));
		} else if(id.equals("kakao")) {
			Map<String, String> nameMap = user.getAttribute("properties");
			Map<String, String> emailMap = user.getAttribute("kakao_account");
			vo.setName(nameMap.get("nickname"));
			vo.setEmail(emailMap.get("email"));
		} else if(id.equals("github")) {
			vo.setName(user.getAttribute("login"));
			vo.setEmail(user.getAttribute("bio"));
		}
		
		session.setAttribute("user", vo); //save session
		
		return user;
	};
	
}
