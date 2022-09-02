package com.justclick.authentication.models;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationInput {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String image;
	private List<String> roles;
}
