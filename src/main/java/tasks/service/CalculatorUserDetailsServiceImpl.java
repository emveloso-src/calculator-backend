package tasks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tasks.model.CalculatorUser;
import tasks.repository.UserRepository;

@Service
public class CalculatorUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CalculatorUser user = userRepository.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException("the user not exists");
		return user;
	}
}