package com.brunodev.taskflow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brunodev.taskflow.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	public Optional<Role> findByName(String name);
}
