package com.fiap.msadminapi.application.produtos.update.requests;

import com.fiap.msadminapi.domain.entity.produto.Imagem;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.infra.model.ImagemModel;

import java.util.Date;
import java.util.List;

public record UpdateProdutoRequest(
        String nome,
        Float valor,
        String descricao,
        CategoriaEnum categoria,
        Integer quantidade,
        Date dataCriacao,
        List<Imagem> imagens) {

}
