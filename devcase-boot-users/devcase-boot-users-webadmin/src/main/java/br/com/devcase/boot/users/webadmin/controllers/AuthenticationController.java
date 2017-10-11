package br.com.devcase.boot.users.webadmin.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

	@RequestMapping("/api/userdetails")
	public ResponseEntity<UserDetails> getUserDetails() {
		
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Object authenticationDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails userDetails = authenticationDetails instanceof UserDetails ? (UserDetails) authenticationDetails : null;
		
		if(userDetails == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(userDetails, HttpStatus.OK);
		}
	}
}
