package org.aom.greeting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

		http.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/hello").authenticated()
				.requestMatchers("/admin").hasRole("ADMIN")
				.requestMatchers("/bye").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/**", "/hi", "/login").permitAll())
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
