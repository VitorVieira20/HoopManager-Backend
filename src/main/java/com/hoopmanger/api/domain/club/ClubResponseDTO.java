package com.hoopmanger.api.domain.club;
import java.util.UUID;

public record ClubResponseDTO(
        UUID id,
        long phone,
        String name,
        String email,
        String instagram,
        String twitter,
        String facebook,
        UUID owner_id
    ) {
}
