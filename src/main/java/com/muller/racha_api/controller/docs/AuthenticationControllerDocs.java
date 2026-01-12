package com.muller.racha_api.controller.docs;

import com.muller.racha_api.dto.AuthenticationDTO;
import com.muller.racha_api.dto.RegisterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Autenticação", description = "Login e Registro de usuários")
public interface AuthenticationControllerDocs {

    @Operation(summary = "Login", description = "Autentica o usuário e retorna um Token JWT")
    String login(@Parameter(description = "Credenciais de login") AuthenticationDTO dto);

    @Operation(summary = "Registro", description = "Cria uma nova conta de usuário")
    void register(@Parameter(description = "Dados de registro") RegisterDTO dto);
}