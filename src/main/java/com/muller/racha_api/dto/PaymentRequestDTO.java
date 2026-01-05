package com.muller.racha_api.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {
    @Schema(description = "Valor do pagamento", example = "7.00")
    @NotNull
    private BigDecimal paymentValue;

    @Schema(description = "URL da imagem do comprovante de pagamento", example = "http://example.com/comprovante.jpg")
    private String imageUrl;

    @Schema(description = "Mensagem do pagamento", example = "Paguei toda minha parte")
    private String message;

}
