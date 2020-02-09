package com.restfulwebservices.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping("hello-world")
	public String helloWorld(){
		return "HelloWorld";
	}
	
	@GetMapping("/hello-world-bean")
	public HelloWorldBean helloWorldBean(){
		return new HelloWorldBean("HelloWorld");
	}
}
