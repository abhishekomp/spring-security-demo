package org.aom.greeting.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity(debug = true)
// this annotation will create the SpringSecurityFilterChain that will by default protect all our end points and also show the login page in the browser when we try to access our end point.
public class SecurityAppConfig {

	@Autowired
	private HttpSecurity http;

	//Requested bean is currently in creation: Is there an unresolvable circular reference?
	//Hence used setter injection for HttpSecurity
	/*
	 * public SecurityAppConfig(HttpSecurity http) { this.http = http; }
	 */

	@Bean
	InMemoryUserDetailsManager setUpUsers() {

		UserDetails john = User.withUsername("john").password("{noop}john").roles("user").build();

		UserDetails admin = User.withUsername("admin").password("{noop}admin").roles("user", "ADMIN").build();

		/*
		 * InMemoryUserDetailsManager userDetailsManager = new
		 * InMemoryUserDetailsManager(); userDetailsManager.createUser(john);
		 * userDetailsManager.createUser(admin);
		 * 
		 * return userDetailsManager;
		 */

		return new InMemoryUserDetailsManager(john, admin);
	}

	// providing our own SecuriryFilterChain
	@Bean
	SecurityFilterChain settingHttpSecurity() throws Exception {

		// http.authorizeHttpRequests().requestMatchers(antMatcher("/hi"),
		// antMatcher("/hello")).authenticated();
		// http.authorizeHttpRequests().requestMatchers("/bye").permitAll();
		// http.formLogin();
		// http.httpBasic();

		/*
		 * http.authorizeHttpRequests(customizer -> {
		 * customizer.requestMatchers(antMatcher("/hi"),
		 * antMatcher("/hello")).authenticated();
		 * customizer.requestMatchers(antMatcher("/bye")).permitAll();
		 * customizer.requestMatchers(antMatcher("/admin")).hasRole("admin"); });
		 */
		// customizer.requestMatchers("/hi", "/hello").authenticated();
		/*
		 * http.formLogin(Customizer.withDefaults());
		 * http.httpBasic(Customizer.withDefaults());
		 */

		/*
		 * This means the following (accessed via browser):
		 * a) /hello endpoint can be accessed by authenticated user only.
		 * b) /admin endpoint can be accessed by authenticated user with Role = ADMIN
		 * c) /bye endpoint can be accessed by authenticated user with either of the two roles (USER, ADMIN)
		 * d) Any endpoint (path) not matching any of the above rules can be accessed by any user.
		 */
		http.authorizeHttpRequests((authorize) -> authorize				
				.requestMatchers("/hello").authenticated()
				.requestMatchers("/admin").hasRole("ADMIN")
				.requestMatchers("/bye").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/**", "/hi", "/login").permitAll()
		)
		.formLogin(withDefaults())
		.httpBasic(withDefaults());

		return http.build();
	}

	@Bean(name = "mvcHandlerMappingIntrospector")
	public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
		return new HandlerMappingIntrospector();
	}

	/*
	 * @Bean InMemoryUserDetailsManager setUpUsers() {
	 * 
	 * GrantedAuthority role1 = new SimpleGrantedAuthority("user"); GrantedAuthority
	 * role2 = new SimpleGrantedAuthority("admin");
	 * 
	 * ArrayList<GrantedAuthority> authoritiesList = new ArrayList<>();
	 * 
	 * UserDetails john = new User("john", "john", authoritiesList);
	 * 
	 * InMemoryUserDetailsManager userDetailsManager = new
	 * InMemoryUserDetailsManager(); userDetailsManager.createUser(john);
	 * 
	 * return userDetailsManager;
	 * 
	 * }
	 */

	/*
	 * @SuppressWarnings("deprecation")
	 * 
	 * @Bean PasswordEncoder noOpPasswordEncoder() { return
	 * NoOpPasswordEncoder.getInstance(); }
	 */
}