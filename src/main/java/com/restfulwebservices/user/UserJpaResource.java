package com.restfulwebservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.restfulwebservices.exception.UserNotFoundException;

@RestController
public class UserJpaResource {

	@Autowired
	private UserDaoService service;
	@Autowired
	private UserRepository  userRepository;

	@GetMapping("/jpa/users")
	public List<User> retriveAllUsers() {
		return userRepository.findAll();

	}

	@GetMapping("/jpa/users/{id}")
	public Optional<User> retriveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if (user == null)
			throw new UserNotFoundException("id-" + id);

		return user;

	}
	  
	@GetMapping("/jpa/users/{id}/post")
	public List<Post> retriveAllUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if (user == null) 
			throw new UserNotFoundException("id-" + id);

		return user.get().getPosts();

	}
	
	
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = null;
		if (user != null) {
			savedUser = userRepository.save(user);
		} else {
			throw new UserNotFoundException("User-" + user);
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}

	@DeleteMapping("/jpa/users/{id}")
	public User deleteUser(@PathVariable int id) {
		User user = service.delete(id);
		if (user == null)
			throw new UserNotFoundException("id-" + id);
		return user;

	}
	
	/*@GetMapping(path="hello-world-internationalized")
	public String helloWorldInternationalized(){
		
		return messageSource.getMessage("good morning message",null,LocaleContextHolder.getLocale());
		
	}*/
	
	
	

}
