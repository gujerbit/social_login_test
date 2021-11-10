package com.gujerbit.social_login_test.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User {

	@JsonProperty
	private String name;
	
	@JsonProperty
	private String email;
	
	@JsonProperty
	private String picture;
	
}
