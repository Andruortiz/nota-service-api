package co.edu.uco.noteserviceapi;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.uco.noteserviceapi.infrastructure.input.filter.RequestLoggingFilter;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class RequestLoggingFilterTest {

    private final RequestLoggingFilter filter = new RequestLoggingFilter();

    @Test
    @DisplayName("Should keep a valid incoming correlation id and clear it afterwards")
    void shouldKeepValidIncomingCorrelationIdAndClearItAfterwards() throws Exception {
        final MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/v1/grupos");
        request.addHeader(RequestLoggingFilter.CORRELATION_HEADER, "abc12345-trace");
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final AtomicReference<String> insideChain = new AtomicReference<>();

        filter.doFilter(request, response, (req, res) ->
                insideChain.set(MDC.get(RequestLoggingFilter.CORRELATION_KEY)));

        assertThat(insideChain.get()).isEqualTo("abc12345-trace");
        assertThat(response.getHeader(RequestLoggingFilter.CORRELATION_HEADER)).isEqualTo("abc12345-trace");
        assertThat(MDC.get(RequestLoggingFilter.CORRELATION_KEY)).isNull();
    }

    @Test
    @DisplayName("Should generate a new correlation id when the incoming one is missing or unsafe")
    void shouldGenerateNewCorrelationIdWhenIncomingOneIsMissingOrUnsafe() throws Exception {
        final MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/v1/grupos");
        request.addHeader(RequestLoggingFilter.CORRELATION_HEADER, "bad\nvalue");
        final MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, (req, res) -> { });

        assertThat(response.getHeader(RequestLoggingFilter.CORRELATION_HEADER))
                .isNotEqualTo("bad\nvalue")
                .matches("[A-Za-z0-9-]{8,64}");
    }
}
