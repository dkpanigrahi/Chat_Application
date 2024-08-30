package com.chat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.entity.JwtRequest;
import com.chat.entity.JwtResponse;
import com.chat.entity.User;
import com.chat.repository.UserRepository;
import com.chat.security.JwtHelper;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;
    
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>("Username already taken", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER"); // Default role
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

	/*
	 * @PostMapping("/login") public ResponseEntity<JwtResponse> login(@RequestBody
	 * JwtRequest request) { this.doAuthenticate(request.getEmail(),
	 * request.getPassword());
	 * 
	 * UserDetails userDetails =
	 * userDetailsService.loadUserByUsername(request.getEmail()); String token =
	 * this.helper.generateToken(userDetails);
	 * 
	 * JwtResponse response = JwtResponse.builder() .jwtToken(token)
	 * .username(userDetails.getUsername()).build(); return new
	 * ResponseEntity<>(response, HttpStatus.OK); }
	 */
    
    
    
    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
    	System.out.println(jwtRequest);
    	
    	try {
			
    		this.manager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
    		
    		
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Bad Credentails");
		}
    	
    	UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getEmail());
    	
    	String token = this.helper.generateToken(userDetails);
    	
    	System.out.println("JWT : "+token);
    	
    	return ResponseEntity.ok(new JwtResponse(token));
    	
    }
    
    
    

	/*
	 * private void doAuthenticate(String email, String password) {
	 * UsernamePasswordAuthenticationToken authentication = new
	 * UsernamePasswordAuthenticationToken(email, password); try {
	 * manager.authenticate(authentication); } catch (BadCredentialsException e) {
	 * throw new BadCredentialsException("Invalid Username or Password!!"); } }
	 */

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid!!";
    }
}
