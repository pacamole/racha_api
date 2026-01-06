package com.muller.racha_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentRequestDTO {
    @Schema(description = "Conteúdo do comentário", example = "Fechou, obrigado por pagar sua parte!")
    @NotBlank
    private String content;
}
