package com.muller.racha_api.controller.docs;

import com.muller.racha_api.dto.UpdateUserDTO;
import com.muller.racha_api.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "Usuários", description = "Gestão de usuários")
public interface UserControllerDocs {

    @Operation(summary = "Listar Usuários", description = "Lista todos os usuários cadastrados (Admin)")
    Page<User> findAllUsers(Pageable pageable);

    @Operation(summary = "Buscar Usuário por ID")
    User findUserById(@Parameter(description = "ID do usuário") String userId);

    @Operation(summary = "Atualizar Usuário por ID")
    User update(
            @Parameter(description = "ID do usuário") String userId,
            UpdateUserDTO dto);

    @Operation(summary = "Atualizar Meu Perfil", description = "Atualiza o usuário logado atualmente")
    User update(
            @Parameter(hidden = true) User user,
            UpdateUserDTO dto);

    @Operation(summary = "Deletar Usuário por ID")
    void delete(@Parameter(description = "ID do usuário") String userId);

    @Operation(summary = "Deletar Minha Conta", description = "Deleta o usuário logado atualmente")
    void delete(@Parameter(hidden = true) User user);
}