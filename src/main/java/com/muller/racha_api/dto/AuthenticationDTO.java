package com.muller.racha_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDTO {
    @Schema(description = "e-mail do usuário", example = "john.doe@gmail.com")
    @NotBlank(message = "O e-mail é obrigatório")
    private String email;

    @Schema(description = "Senha do usuário", example = "1234")
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 4, message = "A senha deve ter no mínimo 4 caracteres")
    private String password;
}