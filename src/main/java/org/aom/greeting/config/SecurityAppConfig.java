package org.aom.greeting.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.*;

@Configuration
@EnableWebSecurity (debug = true)
// this annotation will create the SpringSecurityFilterChain that will by default protect all our end points and also show the login page in the browser when we try to access our end point.
public class SecurityAppConfig {
	
	@Autowired
	private HttpSecurity httpSecurity;
	
	/*
	 * public SecurityAppConfig(HttpSecurity httpSecurity) { this.httpSecurity =
	 * httpSecurity; }
	 */
	
	@Bean
	InMemoryUserDetailsManager setUpUsers() {
		
		UserDetails john = 	
				User.withUsername("john")
				.password("{noop}john")
				.roles("user")
				.build();
	
		UserDetails admin = 	
			User.withUsername("admin")
				.password("{noop}admin")
				.roles("user", "admin")
				.build();
		
		/*
		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
		userDetailsManager.createUser(john);
		userDetailsManager.createUser(admin);
		
		return userDetailsManager;
		*/
		
		return new InMemoryUserDetailsManager(john, admin);
	}
	
	//providing our own SecuriryFilterChain
	@Bean 
	SecurityFilterChain settingHttpSecurity() throws Exception {
	  //httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
	  //httpSecurity.authorizeHttpRequests().requestMatchers("/hi", "/hello").authenticated();
	  httpSecurity.authorizeHttpRequests().requestMatchers(antMatcher("/hi"), antMatcher("/hello")).authenticated();
	  
	  httpSecurity.authorizeHttpRequests().requestMatchers("/bye").permitAll();
	  httpSecurity.formLogin(); 
	  httpSecurity.httpBasic(); 
	  return httpSecurity.build(); 
	 }
	
	@Bean(name = "mvcHandlerMappingIntrospector")
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
	}
	

/*	
	@Bean
	InMemoryUserDetailsManager setUpUsers() {
		
		GrantedAuthority role1 = new SimpleGrantedAuthority("user");
		GrantedAuthority role2 = new SimpleGrantedAuthority("admin");
		
		ArrayList<GrantedAuthority> authoritiesList = new ArrayList<>();
		
		UserDetails john = new User("john", "john", authoritiesList);
		
		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
		userDetailsManager.createUser(john);
		
		return userDetailsManager;
		
	}
*/
	
/*
	@SuppressWarnings("deprecation")
	@Bean
	PasswordEncoder noOpPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
*/
}