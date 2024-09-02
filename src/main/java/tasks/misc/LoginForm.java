package tasks.misc;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginForm {

	@NotNull
	@Size(min = 2, max = 30)
	private String user;

	@NotNull
	@Size(min = 6, max = 30)
	private String password;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
