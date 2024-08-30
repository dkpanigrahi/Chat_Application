package com.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.entity.Message;
import com.chat.entity.User;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	
	List<Message> findBySenderAndReceiver(User sender, User receiver);
}
