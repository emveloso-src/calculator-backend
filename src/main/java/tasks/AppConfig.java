package tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import tasks.auth.JwtAuthenticationFilter;
import tasks.misc.AuthEntryPointJwt;
import tasks.service.implementation.CalculatorUserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity
public class AppConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	CalculatorUserDetailsServiceImpl userDetailsService;
	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

//	@Bean
//	public AuthTokenFilter authenticationJwtTokenFilter() {
//		return new AuthTokenFilter();
//	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(new BCryptPasswordEncoder());

		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	/*
	 * @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws
	 * Exception { // http.csrf(csrf -> csrf.disable()) //
	 * .exceptionHandling(exception ->
	 * exception.authenticationEntryPoint(unauthorizedHandler)) //
	 * .sessionManagement(session ->
	 * session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //
	 * .authorizeHttpRequests(auth -> auth.antMatchers("/api/auth/**").permitAll()
	 * // .anyRequest().authenticated()); // //
	 * http.authenticationProvider(authenticationProvider()); // //
	 * http.addFilterBefore(authenticationJwtTokenFilter(),
	 * UsernamePasswordAuthenticationFilter.class); // // return http.build();
	 * 
	 * http.csrf(csrf -> csrf.disable());
	 * 
	 * // Configure exception handling http.exceptionHandling(exception ->
	 * exception.authenticationEntryPoint(unauthorizedHandler));
	 * 
	 * // Configure session management http.sessionManagement(session ->
	 * session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	 * 
	 * // Configure request authorization // http.authorizeHttpRequests(auth ->
	 * auth.antMatchers("/**").permitAll());
	 * 
	 * // http.authorizeRequests().antMatchers("/**").permitAll() // Allow access to
	 * all endpoints // .anyRequest().permitAll() // Ensure all requests are
	 * permitted // .and().csrf().disable();
	 * 
	 * http.authorizeRequests().antMatchers("/").permitAll() // Allow access to all
	 * endpoints .anyRequest().authenticated() // Ensure all requests are permitted
	 * .and().csrf().disable(); // Optio // Configure authentication provider
	 * http.authenticationProvider(authenticationProvider());
	 * 
	 * // Add custom JWT filter //
	 * http.addFilterBefore(authenticationJwtTokenFilter(),
	 * UsernamePasswordAuthenticationFilter.class);
	 * 
	 * return http.build(); }
	 */

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests().antMatchers("/", "/**", "/api/**").permitAll().anyRequest()
				.authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

// Indicamos que usaremos un filtro
//http.addFilterBefore(authFiltroToken, UsernamePasswordAuthenticationFilter.class);

//		http.csrf().disable().authorizeHttpRequests().antMatchers("/**").permitAll().anyRequest()
//
//				.permitAll().and().sessionManagement()
//
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//				.authenticationProvider(authenticationProvider())
//				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
}