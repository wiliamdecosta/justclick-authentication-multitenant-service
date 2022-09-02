package com.justclick.authentication.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name="id_user")
    private UUID id;
	
	@Column(name="first_name", length=255, nullable = false)
	private String firstName;
	
	@Column(name="last_name", length=255)
	private String lastName;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(length = 255)
	private String image;
	
	@ManyToMany
	@JoinTable(name="users_roles",
		joinColumns = @JoinColumn(name="id_user"),
		inverseJoinColumns = @JoinColumn(name="id_role"))
	private List<Role> roles = new ArrayList<>();
	
	public void addRole(Role role) {
		roles.add(role);
	}
}
