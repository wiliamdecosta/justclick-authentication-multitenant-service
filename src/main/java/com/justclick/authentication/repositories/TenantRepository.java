package com.justclick.authentication.repositories;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.justclick.authentication.entities.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, UUID>{
	
	public Tenant findByEndpoint(String endpoint);
	
	public Tenant findByName(String name);
	
	@Procedure(name="jc.registerNewTenant")
	Map<String, Object> callProcedureRegisterNewTenant(String companyName, 
			String companyAddress, 
			String companyWebsite,
			String companyEmail,
			String companyPhone,
			String companyLogo,
			String companyEndpoint,
			String firstName,
			String lastName,
			String email,
			String password
			);
}
