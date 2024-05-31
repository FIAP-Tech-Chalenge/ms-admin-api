package com.fiap.msadminapi.domain.gateway.produto;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.exception.produto.ProdutoNaoEncontradoException;

import java.util.UUID;

public interface EditaProdutoInterface {
    void editaProduto(Produto produto, UUID uuid) throws ProdutoNaoEncontradoException;
}
