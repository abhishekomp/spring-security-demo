package org.aom.greeting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {

	@Bean
	InMemoryUserDetailsManager setUpUsers() {

		UserDetails john = User.withUsername("john").password("{noop}john").roles("USER").build();
		UserDetails admin = User.withUsername("admin").password("{noop}admin").roles("USER", "ADMIN").build();

		return new InMemoryUserDetailsManager(john, admin);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		/*
		 * Note: these rules should be defined from most specific to most general, so when nothing along the way matches, the last rule is applied. 
		 * This means the following (accessed via browser):
		 * -> A POST request at /hello end point can be accessed by authenticated user with Role = ADMIN.
		 * -> /admin can be accessed by authenticated user with Role = ADMIN.
		 * -> /bye endpoint can be accessed by authenticated user with either of the two roles (USER, ADMIN)
		 * -> /hi and /login end points can be accessed by anyone without authentication.
		 * -> Any other end point requires authentication.
		 */
		http.authorizeHttpRequests((authorize) -> authorize				
				.requestMatchers(HttpMethod.POST, "/hello").hasRole("ADMIN")
				.requestMatchers("/admin").hasRole("ADMIN")
				.requestMatchers("/bye").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/index*", "/hi", "/login").permitAll()
				.anyRequest().authenticated())				
			.formLogin(withDefaults())
			.httpBasic(withDefaults());

		return http.build();
	}

	//needed because we are using spring mvc in this project and hence the MVCRequestMatcher will be used in authorization rules which in turn uses the mvcHandlerMappingIntrospector
	@Bean(name = "mvcHandlerMappingIntrospector")
	public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
		return new HandlerMappingIntrospector();
	}

}
