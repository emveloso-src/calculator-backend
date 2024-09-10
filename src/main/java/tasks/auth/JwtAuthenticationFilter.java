package tasks.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import tasks.service.implementation.CalculatorUserDetailsServiceImpl;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	private final JwtService jwtService;
	private final CalculatorUserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		final String authHeader = req.getHeader("Authorization");
		final String jwt;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			chain.doFilter(req, res);
			return;
		}
		jwt = authHeader.substring(7);
		String username = jwtService.extractUsername(jwt);
		if (username != null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		chain.doFilter(req, res);
	}
}
