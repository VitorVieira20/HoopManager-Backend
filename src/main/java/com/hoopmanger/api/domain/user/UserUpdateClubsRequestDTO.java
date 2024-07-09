package com.hoopmanger.api.domain.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

public record UserUpdateClubsRequestDTO(
        @NotBlank(message = "User clubs is mandatory")
        List<UUID> clubs

) {
}
