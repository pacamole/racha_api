package com.muller.racha_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    @Schema(description = "O nome do usuário", example = "John Doe")
    @NotBlank(message = "O nome do usuário obrigatório")
    private String name;
}
