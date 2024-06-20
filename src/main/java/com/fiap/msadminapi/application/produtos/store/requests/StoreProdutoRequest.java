package com.fiap.msadminapi.application.produtos.store.requests;

import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record StoreProdutoRequest(
        UUID uuid,
        String nome,
        Float valor,
        String descricao,
        CategoriaEnum categoria,
        Integer quantidade,
        Date dataCriacao,

        List<ImagemItem> imagens
) {
}
