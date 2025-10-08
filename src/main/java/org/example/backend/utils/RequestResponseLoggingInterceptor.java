package org.example.backend.utils;

import io.jsonwebtoken.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestResponseLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Incoming Request: Method={}, URI={}, IP={}, Header={}",
                request.getMethod(),
                request.getRequestURI(),
                request.getRemoteAddr(),
                request.getHeader(""));
        // You can also log headers, query parameters, or even the request body here (with caution for performance/memory)
        return true; // Continues the request processing
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("Outgoing Response: Status={}, URI={}",
                response.getStatus(),
                request.getRequestURI());
        if (ex != null) {
            logger.error("Request Exception: ", ex);
        }
    }
}