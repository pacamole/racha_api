package com.muller.racha_api.controller.docs;

import com.muller.racha_api.model.RachaItem;
import com.muller.racha_api.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Convites", description = "Geração e aceitação de convites")
public interface InviteControllerDocs {

    @Operation(summary = "Criar Link de Convite", description = "Gera ou retorna um link existente para entrar no racha")
    String create(
            @Parameter(hidden = true) User user,
            @Parameter(description = "ID do Racha") String rachaId);

    @Operation(summary = "Entrar no Racha (Join)", description = "Adiciona o usuário ao racha usando o ID do convite")
    RachaItem joinRacha(
            @Parameter(hidden = true) User user,
            @Parameter(description = "ID do Convite (extraído do link)") String inviteId);
}