package com.fiap.msadminapi.domain.entity.produto;

import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.exception.produto.CategoriaDeProdutoInvalidaException;


public record CategoriaProtudo(String categoriaEnum) {

    public CategoriaEnum validaCategoriaEnum() throws Exception {
        for (CategoriaEnum enumValue : CategoriaEnum.values()) {
            if (enumValue.name().equalsIgnoreCase(categoriaEnum)) {
                return enumValue;
            }
        }

        throw new CategoriaDeProdutoInvalidaException("Categoria inválida: " + categoriaEnum);
    }
}
