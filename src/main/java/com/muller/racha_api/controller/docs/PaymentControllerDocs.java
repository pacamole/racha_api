package com.muller.racha_api.controller.docs;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.muller.racha_api.model.Payment;
import com.muller.racha_api.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pagamentos", description = "Gerencia os pagamentos")
public interface PaymentControllerDocs {

        @Operation(summary = "Criar pagamento", description = "Cria um pagamento que pode ter um upload de imagem")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Pagamento registrado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos ou erro no upload da imagem"),
        })
        Payment create(
                        @Parameter(hidden = true) User user,
                        @Parameter(description = "ID da Racha") String rachaId,
                        @Parameter(description = "Arquivo comprovante") MultipartFile file,
                        @Parameter(description = "Campos do pagamento") String dataJson)
                        throws Exception;

        @Operation(summary = "Listar pagamentos", description = "Lista todos os pagamentos de uma racha, se o usuário for o representante da racha")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Pagamentos listados com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Pagamentos não foram encontrados com os parâmetros fornecidos"),
        })
        Page<Payment> findAllByRacha(
                        @Parameter(hidden = true) User user,
                        @Parameter(description = "ID da Racha", content = @Content()) String rachaId);

        @Operation(summary = "Atualizar pagamento", description = "Atualiza dados do pagamento")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Pagamentos alterado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Não foi encontrado o pagamento"),
                        @ApiResponse(responseCode = "400", description = "Dados inválidos ou erro no upload da imagem"),
        })
        Payment update(
                        @Parameter(hidden = true) User user,
                        @Parameter(description = "ID da Racha") String rachaId,
                        @Parameter(description = "ID do pagamento") String paymentId,
                        @Parameter(description = "Arquivo comprovante") MultipartFile file,
                        @Parameter(description = "Campos do pagamento") String dataJson) throws Exception;

        @Operation(summary = "Deletar pagamento", description = "Apaga completamente os dados de um pagamento")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Pagamento apagado com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Nao foi encontrado o pagamento"),
        })
        void delete(
                        @Parameter(hidden = true) User user,
                        @Parameter(description = "ID da racha") String rachaId,
                        @Parameter(description = "ID do pagamento") String paymentId);
}
