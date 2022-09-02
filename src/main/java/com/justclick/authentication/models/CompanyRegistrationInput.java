package com.justclick.authentication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyRegistrationInput {
	private String companyName;
	private String companyAddress;
	private String companyWebsite;
	private String companyEmail;
	private String companyPhone;
	private String companyLogo;
	private String companyEndpoint;
	
	//for admin's registration
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
