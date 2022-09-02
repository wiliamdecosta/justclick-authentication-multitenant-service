package com.justclick.authentication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justclick.authentication.entities.User;
import com.justclick.authentication.models.CompanyRegistrationInput;
import com.justclick.authentication.models.CompanyRegistrationOutput;
import com.justclick.authentication.models.ResponseData;
import com.justclick.authentication.models.UserRegistrationInput;
import com.justclick.authentication.services.RegistrationService;

@RestController
@RequestMapping("/v1")
public class RegistrationController {
	
	@Autowired
	RegistrationService registrationService;
	
	@PostMapping("/admin/registration")
	public ResponseEntity<ResponseData<?>> companyRegistration(@RequestBody CompanyRegistrationInput registrationInput) {
		
		CompanyRegistrationOutput registrationOutput = registrationService.registerNewCompany(registrationInput);
		ResponseData<CompanyRegistrationOutput> responseData = new ResponseData<>();
		responseData.setData(registrationOutput);
		
		if(registrationOutput.isSuccess()) {
			responseData.setCode(200);
			responseData.setMessage(registrationOutput.getMessage());
			return new ResponseEntity<ResponseData<?>>(responseData, HttpStatus.OK);
		}else {
			responseData.setCode(400);
			return new ResponseEntity<ResponseData<?>>(responseData, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/user/registration")
	public ResponseEntity<ResponseData<?>> userRegistration(@RequestBody UserRegistrationInput registrationInput) {
		User user = registrationService.userRegistration(registrationInput);
		ResponseData<User> responseData = new ResponseData<>();
		responseData.setData(user);
		responseData.setCode(200);
		responseData.setMessage("create user success");
		return new ResponseEntity<ResponseData<?>>(responseData, HttpStatus.OK);
	}
}
