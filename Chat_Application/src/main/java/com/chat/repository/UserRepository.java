package com.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String email);
	
	public boolean existsByEmail(String email);
	 
	List<User> findAllByIdNot(int id);
}
