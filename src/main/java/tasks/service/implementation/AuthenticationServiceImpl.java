package tasks.service.implementation;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tasks.auth.JwtService;
import tasks.misc.LoginForm;
import tasks.repository.RoleRepository;
import tasks.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

//    public AuthenticationResponse register(RegisterRequest request) {
//        if (userRepository.existsByEmail(request.getEmail()))
//            throw new RuntimeException("email is already in use");
//
//        String password = passwordEncoder.encode(request.getPassword());
//        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
//                .orElseThrow(() -> new RuntimeException("role does not exist"));
//        Set<Role> roles = new HashSet<>();
//        roles.add(userRole);
//
//        User user = User.builder()
//                .email(request.getEmail())
//                .password(password)
//                .firstname(request.getFirstname())
//                .lastname(request.getLastname())
//                .roles(roles)
//                .build();
//
//
//        userRepository.save(user);
//
//        Authentication authentication =
//                authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                                request.getEmail(),
//                                request.getPassword()
//                        )
//                );
//        String token = jwtService.generateToken(authentication);
//
//        return AuthenticationResponse.builder()
//                .token(token)
//                .build();
//    }

	public AuthenticationResponse authenticate(LoginForm request) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUser(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtService.generateToken(authentication);

		User user = (User) authentication.getPrincipal();

		return AuthenticationResponse.builder().token(token).build();
	}

}
