package com.muller.racha_api.controller.docs;

import com.muller.racha_api.dto.PixKeyRequestDTO;
import com.muller.racha_api.model.PixKey;
import com.muller.racha_api.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Chaves Pix", description = "Gerenciamento de chaves Pix do usuário")
public interface PixKeyControllerDocs {

    @Operation(summary = "Cadastrar Chave Pix")
    PixKey create(
            @Parameter(hidden = true) User user,
            @Parameter(description = "Dados da chave") PixKeyRequestDTO dto);

    @Operation(summary = "Listar Minhas Chaves")
    List<PixKey> findAllByUser(@Parameter(hidden = true) User user);

    @Operation(summary = "Atualizar Chave Pix")
    PixKey update(
            @Parameter(hidden = true) User user,
            @Parameter(description = "ID da Chave") String pixId,
            @Parameter(description = "Novos dados") PixKeyRequestDTO dto);

    @Operation(summary = "Deletar Chave Pix")
    void delete(
            @Parameter(hidden = true) User user,
            @Parameter(description = "ID da Chave") String pixId);
}