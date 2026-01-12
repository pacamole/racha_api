package com.muller.racha_api.controller.docs;

import com.muller.racha_api.dto.CommentRequestDTO;
import com.muller.racha_api.model.Comment;
import com.muller.racha_api.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Comentários", description = "Interações nos rachas")
public interface CommentControllerDocs {

    @Operation(summary = "Adicionar Comentário")
    Comment create(
            @Parameter(hidden = true) User user,
            @Parameter(description = "ID do Racha") String rachaId,
            @Parameter(description = "Conteúdo do comentário") CommentRequestDTO dto);

    @Operation(summary = "Listar Comentários")
    List<Comment> findByRacha(
            @Parameter(hidden = true) User user,
            @Parameter(description = "ID do Racha") String rachaId);

    @Operation(summary = "Deletar Comentário")
    void delete(
            @Parameter(hidden = true) User user,
            @Parameter(description = "ID do Comentário") String commentId);
}