package com.fiap.msadminapi.domain.input.produto;

import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.infra.model.ImagemModel;

import java.util.Date;
import java.util.List;

public record EditaProdutoInput(
        String nome,
        Float valor,
        String descricao,
        CategoriaEnum categoria,
        Integer quantidade,
        Date dataCriacao,
        List<ImagemModel> imagens
) {

}
