package com.hoopmanger.api.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table( name= "tb_users" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String email;

    private String password;

    private String role;

    private String plan;

    private List<UUID> clubs;

    private List<UUID> teams;

    private List<UUID> games;

    private List<UUID> athletes;
}
