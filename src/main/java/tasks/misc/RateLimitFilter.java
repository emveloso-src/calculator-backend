package tasks.misc;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class RateLimitFilter extends OncePerRequestFilter {

	private final ConcurrentHashMap<String, Long> requestCounts = new ConcurrentHashMap<>();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String ip = request.getRemoteAddr();
		long currentTime = System.currentTimeMillis();
		requestCounts.putIfAbsent(ip, currentTime);
		long lastRequestTime = requestCounts.get(ip);

		if (currentTime - lastRequestTime < 60000) { // 1 minute
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			return;
		}

		requestCounts.put(ip, currentTime);
		chain.doFilter(request, response);
	}
}
