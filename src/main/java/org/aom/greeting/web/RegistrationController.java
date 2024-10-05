package org.aom.greeting.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class RegistrationController {
	
	// here we are showing the registration page by using thymeleaf integration with spring mvc
	// the user_registration.html is in/WEB-INF/templates/ directory.
	@GetMapping("/register")
	String showNewUserRegistrationPage() {
		//return "/registration.html";
		return "user_registration.html";
	}
}