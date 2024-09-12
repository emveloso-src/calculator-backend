package tasks.service.implementation;

import tasks.misc.LoginForm;

public interface AuthenticationService {

	AuthenticationResponse authenticate(LoginForm request);

}
