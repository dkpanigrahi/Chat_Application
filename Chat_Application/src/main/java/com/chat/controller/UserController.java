package com.chat.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.entity.User;
import com.chat.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/currentUser")
	public ResponseEntity<User> getUserName(Principal p){
		
		String userName = p.getName();
		User loggedInUser = userService.findByEmail(userName);
		return ResponseEntity.ok(loggedInUser);
	}
	
	// Get all users except the logged-in user
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsersExcept(Principal principal) {
        String email = principal.getName();

        // Retrieve the user ID based on email
        User loggedInUser = userService.findByEmail(email);
        if (loggedInUser == null) {
            return ResponseEntity.notFound().build();
        }

        List<User> users = userService.getAllUsersExcept(loggedInUser.getId());
        return ResponseEntity.ok(users);
    }
}
