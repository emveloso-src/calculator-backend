package tasks.misc;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Basic";
	private String username;
	private List<String> roles;
	private Double balance;
	private int id;

	public JwtResponse(String jwt, int id, String username, List<String> roles, Double balance) {
		this.token = jwt;
		this.id = id;
		this.username = username;
		this.roles = roles;
		this.balance = balance;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
