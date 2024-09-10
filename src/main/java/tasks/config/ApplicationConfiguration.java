package tasks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
//
//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration configuration
//    ) throws Exception {
//        return configuration.getAuthenticationManager();
//    }
}
