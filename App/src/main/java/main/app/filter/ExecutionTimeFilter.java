package main.app.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * To log total time taken by a specific request from client
 */
@Slf4j
@Configuration
public class ExecutionTimeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        StopWatch watch = new StopWatch();
        watch.start();
        try {
            chain.doFilter(request, response);
        } finally {
            watch.stop();
            if (log.isDebugEnabled()) {
                log.debug("Request Mapping Method: {} completed within: {} ms", getUri(request), watch.getTotalTimeMillis());
            }

        }
    }
    private String getUri(HttpServletRequest request) {
        return request.getMethod() + ": " + request.getRequestURI()
                + (StringUtils.hasText(request.getQueryString()) ? "?" + request.getQueryString() : "");
    }

}
