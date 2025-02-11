package com.brunodev.taskflow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brunodev.taskflow.model.User;

public interface UserRepository extends JpaRepository <User, Long>{
	
	public Optional<User> findByUsername(String username);
	
	public Optional<User> findByEmail(String email);
}
