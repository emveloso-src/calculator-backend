package tasks.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import tasks.auth.JwtAuthenticationFilter;
import tasks.service.implementation.CalculatorUserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final CalculatorUserDetailsServiceImpl userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
//		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//		AuthenticationManager authenticationManager = builder.build();
//
//		return http.csrf().disable().authorizeHttpRequests().antMatchers(GET, "/").hasAnyAuthority("USER", "ROLE_ADMIN")
//				.antMatchers(GET, "/home").hasAnyAuthority("USER").antMatchers(GET, "/api/v0/operations")
//				.hasAnyAuthority("USER").antMatchers(GET, "/api/v0/records").hasAnyAuthority("USER").anyRequest()
//				.authenticated()
//				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//				.authenticationManager(authenticationManager)
//				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
//	}

}