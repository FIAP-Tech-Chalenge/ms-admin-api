package com.fiap.msadminapi.domain.gateway.produto;

import com.fiap.msadminapi.domain.exception.produto.ProdutoNaoEncontradoException;

import java.util.UUID;

public interface DeletarProdutoInterface {
    void deletaProduto(UUID uuid) throws ProdutoNaoEncontradoException;
}
