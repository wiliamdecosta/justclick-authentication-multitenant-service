package com.justclick.authentication.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tenants")
@Getter
@Setter
@NoArgsConstructor
@NamedStoredProcedureQuery(name = "jc.registerNewTenant",
procedureName = "p_register_new_tenant",
parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name="companyName", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name="companyAddress", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name="companyWebsite", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name="companyEmail", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name="companyPhone", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name="companyLogo", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name="companyEndpoint", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name="firstName", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name="lastName", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name="email", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name="password", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.OUT, name="outMessage", type=String.class),
		@StoredProcedureParameter(mode = ParameterMode.OUT, name="outSuccess", type=Boolean.class),
		@StoredProcedureParameter(mode = ParameterMode.OUT, name="outCreateUserSuccess", type=Boolean.class),
		@StoredProcedureParameter(mode = ParameterMode.OUT, name="outSchemaName", type=String.class)
})
public class Tenant {
	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name="id_tenant")
    private UUID id;
	
	@Column(nullable = false, length=255)
	private String name;
	
	@Column(nullable = false, length=255)
	private String email;
	
	@Column(nullable = false, length=255)
	private String website;
	
	@Column(nullable = false, length=255)
	private String endpoint;
	
	@Column(nullable = false, length=255)
	private String phone;
	
	@Column(nullable = false, length=255)
	private String logo;
	
}
