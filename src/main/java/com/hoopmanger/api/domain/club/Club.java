package com.hoopmanger.api.domain.club;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table( name= "tb_clubs" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Club {
    @Id
    @GeneratedValue
    private UUID id;

    private long phone;

    private String name;

    private String email;

    private String instagram;

    private String facebook;

    private String twitter;

    private UUID owner_id;
}
