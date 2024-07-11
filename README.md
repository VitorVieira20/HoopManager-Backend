# Backend Java Project

## Description

This project is a backend developed in Java using Spring Boot. It provides services to manage clubs, teams, games, and players, with authentication and authorization using JWT (JSON Web Tokens). The project is configured with Maven for dependency management.

## Features

- User management
- Club management
- Team management
- Game management
- Player management
- Authentication and authorization with JWT
- Support for advanced queries and filters

## Technologies

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- Spring Security
- JWT (JSON Web Tokens)
- Flyway
- Lombok
- Mockito
- Maven
- PostgreSQL
- Javax

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-core</artifactId>
        <version>9.8.1</version>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-test</artifactId>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>org.hibernate.validator</groupId>
        <artifactId>hibernate-validator</artifactId>
        <version>6.2.0.Final</version>
    </dependency>
    <dependency>
        <groupId>javax.validation</groupId>
        <artifactId>validation-api</artifactId>
        <version>2.0.1.Final</version>
    </dependency>
    <dependency>
        <groupId>org.glassfish</groupId>
        <artifactId>javax.el</artifactId>
        <version>3.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>io.github.cdimascio</groupId>
        <artifactId>dotenv-java</artifactId>
        <version>2.2.0</version>
    </dependency>

    <dependency>
        <groupId>com.auth0</groupId>
        <artifactId>java-jwt</artifactId>
        <version>4.4.0</version>
    </dependency>
</dependencies>

```

## Getting Started

### Prerequisites

- Java 21
- Maven
- PostgresQL

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/VitorVieira20/HoopManager-Backend.git
    ```

2. Navigate to the project directory:
    ```sh
    cd api
    ```

3. Create a .env file in the root of the project with the following variables:
    ```
    SPRING_DATASOURCE_URL=Postgres_URL
    SPRING_DATASOURCE_USERNAME=postgres
    SPRING_DATASOURCE_PASSWORD=password
    SERVER_PORT=8081
    TOKEN_SECRET_KEY=your_secret_key
   ```

4. Set up the environment variables or edit the `AppConfig` file in `src/main/infra/app`:
    ```java
    @Configuration
        public class AppConfig {
            static {
                Dotenv dotenv = Dotenv.load( );
                System.setProperty( "SPRING_DATASOURCE_URL", dotenv.get( "SPRING_DATASOURCE_URL" ) );
                System.setProperty( "SPRING_DATASOURCE_USERNAME", dotenv.get( "SPRING_DATASOURCE_USERNAME" ) );
                System.setProperty( "SPRING_DATASOURCE_PASSWORD", dotenv.get( "SPRING_DATASOURCE_PASSWORD" ) );
                System.setProperty( "SERVER_PORT", dotenv.get( "SERVER_PORT" ) );
                System.setProperty( "TOKEN_SECRET_KEY", dotenv.get( "TOKEN_SECRET_KEY" ) );
            }
        }
   ```

5. Set up the environment variables or edit the `application.properties` file in `src/main/resources`:
    ```properties
    spring.datasource.url=${SPRING_DATASOURCE_URL}
    spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
    spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
    server.port=${SERVER_PORT}
    api.security.token.secret=${TOKEN_SECRET_KEY}
    ```

### Running the Application

1. Compile and run the project:
    ```sh
    mvn spring-boot:run
    ```

2. The server will be available at http://localhost:8081 or the port specified in the `.env` file.

## API Endpoints

### User

- **GET /api/user/{id}**: Get user information by ID.
- **PUT /api/user/{id}**: Update user information by ID.

### Auth

- **POST /api/auth/login**: Validate login and send authentication token to the frontend.
- **POST /api/auth/register**: Create a new user with the provided information.

### Club

- **GET /api/club/{clubId}**: Get club information by ID.
- **GET /api/club/owner/{ownerId}**: Get a list of clubs belonging to the `ownerId`.
- **GET /api/club/name/{clubName}**: Get a list of clubs with the specified name.
- **GET /api/club/favs/{userId}**: Get a list of the user's favorite clubs.
- **POST /api/club**: Create a new club.
- **PUT /api/club/{clubId}**: Update club information by ID.
- **DELETE /api/club/{clubId}**: Delete club by ID.

### Team

- **GET /api/team/{teamId}**: Get team information by ID.
- **GET /api/team/owner/{ownerId}**: Get a list of teams belonging to the `ownerId`.
- **GET /api/team/club/{clubId}**: Get a list of teams belonging to the `club`.
- **GET /api/team/name/{teamName}**: Get a list of teams with the specified name.
- **GET /api/team/favs/{userId}**: Get a list of the user's favorite teams.
- **POST /api/teams**: Create a new team.
- **PUT /api/teams/{teamId}**: Update team information by ID.
- **DELETE /api/teams/{teamId}**: Delete team by ID.

### Game

- **GET /api/game/{gameId}**: Get game information by ID.
- **GET /api/game/team/{teamId}**: Get a list of games for the specified `teamId`.
- **GET /api/game/owner/{ownerId}**: Get a list of games for the specified `ownerId`.
- **POST /api/game**: Create a new game.
- **PUT /api/game/{gameId}**: Update game information by ID.
- **DELETE /api/game/{gameId}**: Delete game by ID.

### Player

- **GET /api/player/{playerId}**: Get player information by ID.
- **GET /api/player/team/{teamId}**: Get a list of players belonging to a `team`.
- **GET /api/player/owner/{ownerId}**: Get a list of players belonging to an `owner`.
- **GET /api/player/game/{gameId}**: Get a list of players belonging to a `game`.
- **GET /api/player/gameInfo/{gameInfoId}**: Get a list of players in a game without stats (points, assists, and rebounds).
- **GET /api/player/name/{playerName}**: Get a list of players with the specified name.
- **GET /api/player/favs/{userId}**: Get a list of the user's favorite players.
- **POST /api/players**: Create a new player.
- **PUT /api/players/{id}**: Update player information by ID.
- **DELETE /api/players/{id}**: Delete player by ID.

### GameInfo

- **GET /api/gameInfo/{gameInfoId}**: Get game info by ID.
- **GET /api/gameInfo/game/{gameId}**: Get a list of game info for the specified `gameId`.
- **POST /api/gameInfo**: Create new game info.
- **PUT /api/gameInfo/{gameInfoId}**: Update game info by ID.
- **DELETE /api/gameInfo/{gameInfoId}**: Delete game info by ID.


## Authentication

We use JSON Web Tokens (JWT) for authentication. To access protected endpoints, you need to include a valid token in the request header:

```http
Authorization: Bearer <your-token>
````

## Test
To run the tests, execute the following command:

   ```sh
    mvn test
   ```