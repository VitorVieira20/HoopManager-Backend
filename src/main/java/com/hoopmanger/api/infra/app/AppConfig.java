package com.hoopmanger.api.infra.app;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    static {
        Dotenv dotenv = Dotenv.load( );
        System.setProperty( "SPRING_DATASOURCE_URL", dotenv.get( "SPRING_DATASOURCE_URL" ) );
        System.setProperty( "SPRING_DATASOURCE_USERNAME", dotenv.get( "SPRING_DATASOURCE_USERNAME" ) );
        System.setProperty( "SPRING_DATASOURCE_PASSWORD", dotenv.get( "SPRING_DATASOURCE_PASSWORD" ) );
        System.setProperty( "SERVER_PORT", dotenv.get( "SERVER_PORT" ) );
    }
}
