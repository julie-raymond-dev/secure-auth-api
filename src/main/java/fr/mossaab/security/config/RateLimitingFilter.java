package fr.mossaab.security.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple IP-based rate-limiting filter using Bucket4j.
 * Limits each client IP to 100 requests per minute.
 */
@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final long CAPACITY = 100;
    private static final Duration DURATION = Duration.ofMinutes(1);
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        Bucket bucket = cache.computeIfAbsent(ip, this::newBucket);
        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(429);
            response.getWriter().write("Too many requests");
        }
    }

    private Bucket newBucket(String key) {
        Refill refill = Refill.greedy(CAPACITY, DURATION);
        Bandwidth limit = Bandwidth.classic(CAPACITY, refill);
        return Bucket.builder().addLimit(limit).build();
    }
}
