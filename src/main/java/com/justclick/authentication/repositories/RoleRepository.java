package com.justclick.authentication.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justclick.authentication.entities.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {
	public Role findByName(String name);
}
