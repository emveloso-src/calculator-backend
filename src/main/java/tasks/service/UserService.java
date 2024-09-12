package tasks.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tasks.auth.JwtService;
import tasks.exception.InvalidPasswordException;
import tasks.exception.UserNotFoundException;
import tasks.misc.JwtResponse;
<<<<<<< HEAD
=======
import tasks.misc.JwtUtils;
>>>>>>> ea9752672413964368aac3c63171767ce4b09bab
import tasks.misc.LoginForm;
import tasks.model.CalculatorUser;
import tasks.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
<<<<<<< HEAD
	JwtService jwtUtils;
=======
	JwtUtils jwtUtils;
>>>>>>> ea9752672413964368aac3c63171767ce4b09bab

	@Autowired
	AuthenticationManager authenticationManager;

	public ResponseEntity<JwtResponse> login(LoginForm form) throws UserNotFoundException, InvalidPasswordException {

		CalculatorUser user = userRepository.findByUsername(form.getUser());
		if (user == null)
			throw new UserNotFoundException("User not found");

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(form.getUser(), form.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
<<<<<<< HEAD
		String jwt = jwtUtils.generateToken(authentication);
=======
		String jwt = jwtUtils.generateJwtToken(authentication);
>>>>>>> ea9752672413964368aac3c63171767ce4b09bab

		CalculatorUser userDetails = (CalculatorUser) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity
				.ok(new JwtResponse(jwt, user.getId(), userDetails.getUsername(), roles, user.getBalance()));

	}

}
