package org.aom.greeting.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ByeController {
	
	@GetMapping("/bye")
	String sayBye() {
		return "bye everyone";
	}

}
