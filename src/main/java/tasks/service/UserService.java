package tasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tasks.exception.InvalidPasswordException;
import tasks.exception.UserNotFoundException;
import tasks.misc.LoginForm;
import tasks.model.CalculatorUser;
import tasks.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public CalculatorUser login(LoginForm form) throws UserNotFoundException, InvalidPasswordException {
		CalculatorUser user = userRepository.findByUsername(form.getUser());
		if (user == null)
			throw new UserNotFoundException("User not found");

		if (!user.getPassword().equals(form.getPassword())) {
			throw new InvalidPasswordException("Invalid password");
		}
		return user;
	}
}
