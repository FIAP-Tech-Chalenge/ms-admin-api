package com.fiap.msadminapi.application.produtos.update.requests;

import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;

import java.util.Date;

public record UpdateProdutoRequest(
        String nome,
        Float valor,
        String descricao,
        CategoriaEnum categoria,
        Integer quantidade,
        Date dataCriacao) {
}
