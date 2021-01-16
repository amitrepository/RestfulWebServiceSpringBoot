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

@RestController
public class UserJpaResource {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@GetMapping("/jpa/users")
	public List<UserEntity> retriveAllUsers() {
		return userRepository.findAll();

	}

	@GetMapping("/jpa/users/{id}")
	public Optional<UserEntity> retriveUser(@PathVariable int id) {
		Optional<UserEntity> user = userRepository.findById(id);
		if (!user.isPresent())
			throw new UserNotFoundException("id-" + id);

		return user;

	}

	@GetMapping("/jpa/users/{id}/post")
	public List<Post> retriveAllUser(@PathVariable int id) {
		Optional<UserEntity> user = userRepository.findById(id);
		if (user == null)
			throw new UserNotFoundException("id-" + id);

		return user.get().getPosts();

	}

	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody UserEntity user) {
		@Valid UserEntity savedUser = null;
		if (user != null) {
			savedUser = userRepository.save(user);
		} else {
			throw new UserNotFoundException("User-" + user);
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}

	@DeleteMapping("/jpa/user/{id}")
	public void delete(@PathVariable int id) {
		userRepository.deleteById(id);

	}

	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		 userRepository.deleteById(id);
		
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
		
		Optional<UserEntity> userOptional = userRepository.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}

		UserEntity user = userOptional.get();
		
		post.setUser(user);
		
		postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}

}
