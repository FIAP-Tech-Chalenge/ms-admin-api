package com.fiap.msadminapi.domain.input.produto;


import com.fiap.msadminapi.domain.entity.produto.Imagem;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;

import java.util.Date;
import java.util.List;

public record CriarProdutoInput(
        String nome,
        Float valor,
        String descricao,
        CategoriaEnum categoria,
        Integer quantidade,
        Date dataCriacao,
        List<Imagem> imagens
) {
}
