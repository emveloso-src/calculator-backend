package tasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tasks.exception.InvalidPasswordException;
import tasks.exception.UserNotFoundException;
import tasks.misc.ErrorResponse;
import tasks.misc.JwtUtils;
import tasks.misc.LoginForm;
import tasks.service.UserService;

/**
 * User controller to login using credentials
 * 
 * @author emiliano.veloso
 *
 */
//@CrossOrigin(origins = "https://calculator-frontend20240731-4ffe7a2408f3.herokuapp.com/")
@CrossOrigin(origins = "http://localhost:5173/")
@RestController
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	AuthenticationManager authenticationManager;

	@PostMapping("/api/v0/login")
	public ResponseEntity<?> login(@RequestBody LoginForm login) {
		try {
			return ResponseEntity.ok(userService.login(login));
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
		} catch (InvalidPasswordException e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
