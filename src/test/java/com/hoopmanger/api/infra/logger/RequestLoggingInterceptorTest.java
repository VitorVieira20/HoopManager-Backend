package com.hoopmanger.api.infra.logger;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.util.ContextInitializer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class RequestLoggingInterceptorTest {

    private RequestLoggingInterceptor requestLoggingInterceptor;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Object handler;

    @Mock
    private Exception exception;

    private TestAppender testAppender;
    private Logger logger;

    @BeforeEach
    public void setUp( ) {
        MockitoAnnotations.openMocks( this );
        requestLoggingInterceptor = new RequestLoggingInterceptor( );

        testAppender = new TestAppender( );
        logger = ( Logger ) LoggerFactory.getLogger( RequestLoggingInterceptor.class );
        logger.addAppender( testAppender );
        testAppender.start( );
    }

    @AfterEach
    public void tearDown( ) {
        logger.detachAppender( testAppender );
        testAppender.clear( );
    }

    @Test
    @DisplayName( "Test Request Completed With An Error" )
    public void testAfterCompletion_WithException( ) {
        when( exception.getMessage( ) ).thenReturn( "Test Exception" );

        requestLoggingInterceptor.afterCompletion( request, response, handler, exception );

        List<ILoggingEvent> logEvents = testAppender.getEvents( );
        boolean found = logEvents.stream( )
                .anyMatch( event -> event.getFormattedMessage( ).contains("Request completed with error: Test Exception" ) );

        assertTrue( found, "Expected log message not found" );
    }

    @Test
    @DisplayName( "Test Request Completed Successfully" )
    public void testAfterCompletion_WithoutException( ) {
        requestLoggingInterceptor.afterCompletion( request, response, handler, null );

        List<ILoggingEvent> logEvents = testAppender.getEvents( );
        boolean found = logEvents.stream( )
                .anyMatch( event -> event.getFormattedMessage( ).contains("Request completed successfully" ) );

        assertTrue( found, "Expected log message not found" );
    }
}
