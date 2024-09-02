package tasks.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents user containing username, password and balance
 * 
 * @author emiliano.veloso
 *
 */
@Entity
public class CalculatorUser implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;
	private String password;
	private Double balance;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Override
	public ArrayList<GrantedAuthority> getAuthorities() {

		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		GrantedAuthority grantedAuthority = new GrantedAuthority() {

			@Override
			public String getAuthority() {
				return "ADMIN";
			}

		}; // "ADMIN";
		authorities.add(grantedAuthority);
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return balance > -1;
	}

}
