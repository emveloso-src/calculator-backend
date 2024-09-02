package tasks.misc;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private final String SECRET_KEY = "your-secret-key";

	public String generateToken(UserDetails userDetails) {
		return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Claims extractClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}

	public boolean isTokenExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date());
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		return (userDetails.getUsername().equals(extractUsername(token)) && !isTokenExpired(token));
	}
}
