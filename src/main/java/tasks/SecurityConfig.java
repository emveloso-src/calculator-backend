package tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import tasks.misc.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/public/**").permitAll().anyRequest().authenticated()
				.and().addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/user/**")
//				.hasRole("USER").anyRequest().authenticated();
//	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeRequests().antMatchers("/**").permitAll() // Adjust as needed
//				.and().cors(); // Enable CORS
//	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins("http://localhost:5173")
//						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*");
//			}
//		};
//	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable() // Disable CSRF for simplicity, consider enabling it in production
//				.authorizeRequests().antMatchers("/").permitAll() // Allow public access to login page
//				.anyRequest().authenticated() // All other requests require authentication
//				.and().formLogin().loginPage("/") // Specify the custom login page URL
//				.permitAll() // Allow everyone to access the login page
//				.and().logout().permitAll().and().cors(); // Allow everyone to access the logout page
//	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("password")).roles("USER")
//				.and().withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
