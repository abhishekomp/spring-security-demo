package org.aom.greeting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity (debug = true)
// this annotation will create the SpringSecurityFilterChain that will by default protect all our end points and also show the login page in the browser when we try to access our end point.
public class SecurityAppConfig {

}
