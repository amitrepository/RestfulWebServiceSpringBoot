package com.restfulwebservices.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "user Not Found")
public class UserNotFoundException extends RuntimeException{

	public UserNotFoundException(String message) {
		super(message);
		
	}
	

}
