package com.justclick.authentication.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justclick.authentication.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
