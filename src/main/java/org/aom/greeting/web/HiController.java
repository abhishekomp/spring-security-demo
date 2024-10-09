package org.aom.greeting.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
	
	private String getLoggedInUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = "";
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		System.out.println("Current user is " + username);
		return username;
	}
	
	@GetMapping("/hi")
	String sayHi() {
		getLoggedInUser();
		return "hi everyone";
	}
	
	@GetMapping("/hello")
	String sayHello() {
		//return "hello everyone";
		return String.format("Hello %s, how are you today?", getLoggedInUser());
	}
	
	@GetMapping("/admin")
	String adminEndPoint() {
		//return "hello everyone, this is admin page";
		return String.format("Hello %s, how are you today?", getLoggedInUser());
	}
}