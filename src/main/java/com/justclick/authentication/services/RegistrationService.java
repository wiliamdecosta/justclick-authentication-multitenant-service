package com.justclick.authentication.services;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justclick.authentication.entities.Role;
import com.justclick.authentication.entities.User;
import com.justclick.authentication.models.CompanyRegistrationInput;
import com.justclick.authentication.models.CompanyRegistrationOutput;
import com.justclick.authentication.models.UserRegistrationInput;
import com.justclick.authentication.providers.DataSourceMultitenantConnectionProvider;
import com.justclick.authentication.repositories.RoleRepository;
import com.justclick.authentication.repositories.TenantRepository;
import com.justclick.authentication.repositories.UserRepository;

@Service
public class RegistrationService {
	
	@Autowired
	private TenantRepository tenantRepo;
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	public CompanyRegistrationOutput registerNewCompany(CompanyRegistrationInput registrationInput) {
		String endPoint = "/" + registrationInput.getCompanyName()
											.trim()
											.replace(". ","-")
											.replace(".","-")
											.replace(" ", "-")
											.toLowerCase();
		
		registrationInput.setCompanyEndpoint(endPoint);
		Map<String, Object> outputParam = tenantRepo.callProcedureRegisterNewTenant(
				registrationInput.getCompanyName(), 
				registrationInput.getCompanyAddress(),
				registrationInput.getCompanyWebsite(), 
				registrationInput.getCompanyEmail(), 
				registrationInput.getCompanyPhone(), 
				registrationInput.getCompanyLogo(), 
				registrationInput.getCompanyEndpoint(), 
				registrationInput.getFirstName(), 
				registrationInput.getLastName(), 
				registrationInput.getEmail(), 
				registrationInput.getPassword());
		
		CompanyRegistrationOutput registrationOutput = new CompanyRegistrationOutput();
		outputParam.forEach((key, value) -> {
			if(key.compareTo("outMessage") == 0) {
				registrationOutput.setMessage((String)value);
			}
			
			if(key.compareTo("outSuccess") == 0) {
				registrationOutput.setSuccess((boolean)value);
			}
			
			if(key.compareTo("outCreateUserSuccess") == 0) {
				registrationOutput.setCreateUserSuccess((boolean)value);
			}
			
			if(key.compareTo("outSchemaName") == 0) {
				registrationOutput.setSchemaName((String)value);
			}
			
		});
		
		if(registrationOutput.isSuccess()) {
			addDataSource(registrationOutput.getSchemaName());
		}
		
		return registrationOutput;
	}
	
	private void addDataSource(String schemaName) {
		DataSourceMultitenantConnectionProvider connectionProvider = context.getBean(DataSourceMultitenantConnectionProvider.class);
		connectionProvider.addDataSource(schemaName);
	}
	
	public User userRegistration(UserRegistrationInput registrationInput) {
		User user = new User();
		user.setFirstName(registrationInput.getFirstName());
		user.setLastName(registrationInput.getLastName());
		user.setEmail(registrationInput.getEmail());
		user.setPassword(registrationInput.getPassword());
		user.setImage(registrationInput.getImage());
		
		for (String role : registrationInput.getRoles()) {
			Role roleEntity = roleRepo.findByName(role);
			user.addRole(roleEntity);
		}
		
		return userRepo.save(user);
	}
}
