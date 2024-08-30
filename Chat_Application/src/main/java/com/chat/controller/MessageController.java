package com.chat.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chat.entity.Message;
import com.chat.entity.User;
import com.chat.repository.MessageRepository;
import com.chat.repository.UserRepository;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "http://localhost:4200")
public class MessageController {

	@Autowired
    private UserRepository userRepo;
    
    @Autowired
    private MessageRepository messageRepo;
    
    
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Message>> loadMessageByUser(@PathVariable("id") int userId, Principal principal) {
        // Find the selected user
        User selectedUser = userRepo.findById(userId).orElse(null);
        if (selectedUser == null) {
            return ResponseEntity.notFound().build(); // Return 404 if the user is not found
        }

        // Get the logged-in user using the Principal
        String loggedInUserEmail = principal.getName();
        User loggedInUser = userRepo.findByEmail(loggedInUserEmail);
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
        }

        // Fetch messages between the logged-in user and the selected user
        List<Message> messages = messageRepo.findBySenderAndReceiver(loggedInUser, selectedUser);
        messages.addAll(messageRepo.findBySenderAndReceiver(selectedUser, loggedInUser));
        for(Message l: messages) {
        	System.out.println(l);
        }
        
        return ResponseEntity.ok(messages);
    }
    
    
    
    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestParam("receiverId") int receiverId,
                                               @RequestParam("content") String content,
                                               Principal principal) {
        // Get the logged-in user (sender) using Principal
        String senderEmail = principal.getName();
        User sender = userRepo.findByEmail(senderEmail);
        if (sender == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Find the receiver
        User receiver = userRepo.findById(receiverId).orElse(null);
        if (receiver == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Create a new message
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);

        // Save the message
        Message savedMessage = messageRepo.save(message);
        

        // Return the saved message
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }


    
}
