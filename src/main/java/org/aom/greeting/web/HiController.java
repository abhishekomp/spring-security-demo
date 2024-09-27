package org.aom.greeting.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
	
	@GetMapping("/hi")
	String sayHi() {
		return "hi everyone";
	}
	
	@GetMapping("/hello")
	String sayHello() {
		return "hello everyone";
	}

}
