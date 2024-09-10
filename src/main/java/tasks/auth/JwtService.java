package tasks.auth;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	public String generateToken(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return Jwts.builder().setSubject(userDetails.getUsername()).claim("roles", getRoles(userDetails))
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration * 60 * 60 * 1000))
				.signWith(getKey(secret)).compact();
	}

	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(getKey(secret)).build().parseClaimsJws(token).getBody().getSubject();
	}

	private List<String> getRoles(UserDetails userDetails) {
		return userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
	}

	private Key getKey(String secret) {
		byte[] secretBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(secretBytes);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

}
