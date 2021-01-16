package com.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {
	@Autowired
	UserDaoService service;
	
	@GetMapping("/users")
	public List<User> retriveAllUser(){
		return service.findAll();
		
	}
	
	@GetMapping("/users/{id}")
	public User retriveUser(@PathVariable int id){
		User user= service.findOne(id);
		
		if(user==null) {
			throw new UserNotFoundException("id" +id);
		}
		return user;
		
	}
	
	@PostMapping("/users")
	public  ResponseEntity<Object> createUser(@Valid @RequestBody User user){
		User savedUser = null;
		if (user != null) {
			savedUser = service.save(user);
		} else {
			throw new UserNotFoundException("User-" + user);
		}
		
		URI location=ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("{/id}").buildAndExpand(savedUser.getId()).toUri();
		
		
		return ResponseEntity.created(location).build();
	}
	
	
	@DeleteMapping("/users/{id}")
	public User deleteUser(@PathVariable int id) {
		User user = service.delete(id);
		if (user == null)
			throw new UserNotFoundException("id-" + id);
		return user;

	}
 
}
