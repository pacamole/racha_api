package com.muller.racha_api.controller.docs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.muller.racha_api.dto.RachaItemRequestDTO;
import com.muller.racha_api.model.RachaItem;
import com.muller.racha_api.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Rachas", description = "Gerenciamento dos grupos de divisão de despesas")
public interface RachaControllerDocs {

    @Operation(summary = "Criar Racha", description = "Cria um novo grupo de racha")
    RachaItem create(
            @Parameter(description = "Dados do novo racha", required = true) RachaItemRequestDTO dto,
            @Parameter(hidden = true) User user);

    @Operation(summary = "Listar Meus Rachas", description = "Retorna lista paginada dos rachas onde sou representante")
    Page<RachaItem> findAllByUser(
            Pageable pageable,
            @Parameter(hidden = true) User user);

    @Operation(summary = "Atualizar Racha")
    RachaItem update(
            @Parameter(description = "ID do Racha") String rachaId,
            @Parameter(description = "Dados atualizados") RachaItemRequestDTO dto,
            @Parameter(hidden = true) User user);

    @Operation(summary = "Deletar Racha")
    ResponseEntity<HttpStatus> delete(
            @Parameter(description = "ID do Racha") String rachaId,
            @Parameter(hidden = true) User user);
}