package tasks.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tasks.misc.LoginForm;
import tasks.model.CalculatorUser;
import tasks.repository.UserRepository;

@Service
public class AuthenticationService {
	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

//	public User signup(RegisterUserDto input) {
//		User user = new User().setFullName(input.getFullName()).setEmail(input.getEmail())
//				.setPassword(passwordEncoder.encode(input.getPassword()));
//
//		return userRepository.save(user);
//	}

	public CalculatorUser authenticate(LoginForm input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getUser(), input.getPassword()));

		return userRepository.findByUsername(input.getUser());
	}
}