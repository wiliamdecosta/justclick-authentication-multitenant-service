package com.justclick.authentication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyRegistrationOutput {
	private String message;
	private boolean success;
	private boolean createUserSuccess;
	private String schemaName;
}
