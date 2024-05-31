package com.fiap.msadminapi.application.produtos.store.requests;

import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;

import java.util.Date;
import java.util.List;

public record StoreProdutoRequest(
        String nome,
        Float valor,
        String descricao,
        CategoriaEnum categoria,
        Integer quantidade,
        Date dataCriacao,

        List<ImagemItem> imagens
) {
}
