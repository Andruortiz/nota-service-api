package co.edu.uco.noteserviceapi.infrastructure.input.filter;

import co.edu.uco.noteserviceapi.crosscutting.util.helper.UuidHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {

    public static final String CORRELATION_HEADER = "X-Correlation-Id";
    public static final String CORRELATION_KEY = "correlationId";

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private static final Pattern VALID_ID = Pattern.compile("[A-Za-z0-9-]{8,64}");

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain) throws ServletException, IOException {

        final String correlationId = resolveCorrelationId(request.getHeader(CORRELATION_HEADER));
        MDC.put(CORRELATION_KEY, correlationId);
        response.setHeader(CORRELATION_HEADER, correlationId);

        final long start = System.nanoTime();
        try {
            chain.doFilter(request, response);
        } finally {
            LOGGER.info("{} {} -> {} ({} ms)",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    (System.nanoTime() - start) / 1_000_000);
            MDC.remove(CORRELATION_KEY);
        }
    }

    private String resolveCorrelationId(final String received) {
        return received != null && VALID_ID.matcher(received).matches()
                ? received
                : UuidHelper.generate().toString();
    }
}
