package com.hoopmanger.api.infra.logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) {
        logger.info( "Incoming request: {} {} from {}", request.getMethod( ), request.getRequestURI( ), request.getRemoteAddr( ) );
        return true;
    }

    @Override
    public void afterCompletion( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex ) {
        if ( ex != null ) {
            logger.error("Request completed with error: {}", ex.getMessage( ) );
        } else {
            logger.info( "Request completed successfully" );
        }
    }
}
