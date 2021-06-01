package com.realizer.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realizer.security1.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
