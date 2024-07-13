package com.hoopmanger.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class ApiApplicationTests {

	@Test
	@DisplayName( "Test Context Loads" )
	void contextLoads( ) {
	}

	@Test
	@DisplayName( "Test Application" )
	void main( ) {
		assertDoesNotThrow( ( ) -> ApiApplication.main( new String[]{} ) );
	}

}
