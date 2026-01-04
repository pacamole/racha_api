package com.muller.racha_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RachaItemRequestDTO {
    @Schema(name = "Título do item de racha", example = "Uber")
    @NotBlank(message = "O título é obrigatório")
    private String title;
    @Schema(name = "Descrição do item de racha", example = "Passagem até a rodoviária")
    private String description;
    @Schema(name = "Preço total do item de racha", example = "14.00")
    @Nonnull
    private BigDecimal totalPrice;
    @Schema(name = "Data de vencimento do item de racha", example = "2024-12-31T23:59:59")
    private LocalDateTime dueDate;
    @NotBlank(message = "É necessário informar se o representante irá participar do racha")
    private boolean representative_participate;
}
