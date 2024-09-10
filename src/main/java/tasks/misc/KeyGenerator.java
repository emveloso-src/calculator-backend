package tasks.misc;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.jsonwebtoken.security.Keys;

public class KeyGenerator {

	public static void main(String[] args) {
		// Generate a secure key for HMAC-SHA-256
		SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
		// Encode key to a Base64 string for easier storage or display
		String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());

		System.out.println("Generated Key: " + key);
		System.out.println("Generated Key (Base64): " + base64Key);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode("1234");
		System.out.println(encodedPassword);
	}
}