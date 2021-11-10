package com.gujerbit.social_login_test.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.gujerbit.social_login_test.user.User;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final HttpSession session;
	private User user;
	
	@Value("${v-front.client-id}")
	private String id;
	
	@Value("${v-front.client-secret}")
	private String secret;
	
	@Value("${v-front.request-factor-uri-prefix}")
	private String prefix;
	
	@Value("${v-front.request-factor-uri-suffix}")
	private String suffix;
	
	@Value("${v-front.request-token-uri}")
	private String tokenUri;
	
	@Value("${v-front.request-token-param-header1}")
	private String tokenHeader1;
	
	@Value("${v-front.request-token-param-header2}")
	private String tokenHeader2;
	
	@Value("${v-front.request-token-param-header3}")
	private String tokenHeader3;
	
	@Value("${v-front.request-token-param-header4}")
	private String tokenHeader4;
	
	@Value("${v-front.request-token-param-header5}")
	private String tokenHeader5;
	
	@Value("${v-front.request-token-param1}")
	private String tokenParam1;
	
	@Value("${v-front.request-token-param2}")
	private String tokenParam2;
	
	@Value("${v-front.request-user-uri}")
	private String userUri;
	
	private String code = "";
	
//	@GetMapping("/user_info")
//	public @ResponseBody String[] login() {
//		if(session.getAttribute("user") != null) { //if session exists
//			user = (User) session.getAttribute("user"); //get session attribute
//			String[] result = {user.getName(), user.getEmail()}; //new string array
//			
//			return result; //bring to front user info
//		} else {
//			return null;
//		}
//	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req, HttpServletResponse res) {
		System.out.println(session.getId());
		System.out.println(req.getSession().getId());
		System.out.println(session.getAttribute("user"));
//		Cookie cookie = new Cookie("JSESSIONID", null);
//		cookie.setMaxAge(0);
//		cookie.setPath("/");
//		res.addCookie(cookie);
//		session.removeAttribute("user");
		session.invalidate();
		System.out.println(session.getId());
		System.out.println(req.getSession().getId());
		System.out.println(session.getAttribute("user"));
		
		return "redirect:/login";
	}
	
	@GetMapping("/")
	public String aircuveAuth() {
		return prefix + id + suffix;
	}
	
	@GetMapping("/info")
	public String getCode(HttpServletRequest req) {
		this.code = req.getParameter("code");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>(); //linked multi map save -> ordered multi map
		params.add(tokenHeader1, code);
		params.add(tokenHeader2, id );
		params.add(tokenHeader3, secret);
		params.add(tokenHeader4, tokenParam1);
		params.add(tokenHeader5, tokenParam2);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "*");
		headers.add("Access-Control-Allow-Headers", "*");
		headers.add("Content-type", "application/x-www-form-urlencoded");
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String,String>>(params, headers); //headers + body
		
		RestTemplate template = new RestTemplate(); //restful http connection
		template.setRequestFactory(new HttpComponentsClientHttpRequestFactory()); //401 no body get
		
		ResponseEntity<Map> res = template.exchange(tokenUri, HttpMethod.POST, entity, Map.class); //exchange -> all type
		String accessToken = (String) res.getBody().get("access_token");
		String userName = "";
		
		if(res.getStatusCode().ACCEPTED != null) { //200 ok
			params = new LinkedMultiValueMap<String, String>();
			headers = new HttpHeaders();
			headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
			
			HttpEntity<MultiValueMap<String, String>> userEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
			
			ResponseEntity<Map> result = template.exchange(userUri, HttpMethod.POST, userEntity, Map.class);
			System.out.println(result.getBody());
			userName = (String) result.getBody().get("user_name");	
		}
		
		if(session.getAttribute("user") != null) user = (User) session.getAttribute("user");
		
		Map<String, String> finalAuthorizationMap = new HashMap<String, String>();
		finalAuthorizationMap.put("oneFactorUserName", user.getName());
		finalAuthorizationMap.put("email", user.getEmail());
		finalAuthorizationMap.put("accessToken", accessToken);
		finalAuthorizationMap.put("twoFactorUserName", userName);
		
		session.setAttribute("finalFactorUser", finalAuthorizationMap);
		
		return "redirect:/auth_info";
	}
	
	@GetMapping("/final_factor_user_info")
	public @ResponseBody Map<String, String> getFinalFactorUserInfo() {
		return (Map<String, String>) session.getAttribute("finalFactorUser") != null ? (Map<String, String>) session.getAttribute("finalFactorUser") : null;
	}
	
}
