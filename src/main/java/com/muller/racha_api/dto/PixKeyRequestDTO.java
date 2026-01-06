package com.muller.racha_api.dto;

import com.muller.racha_api.model.PixType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PixKeyRequestDTO {

    @Schema(description = "A chave Pix", example = "john.doe@gmail.com")
    @NotBlank
    private String key;

    @Schema(description = "O tipo da chave Pix", example = "EMAIL")
    @NotNull
    private PixType type;
}
