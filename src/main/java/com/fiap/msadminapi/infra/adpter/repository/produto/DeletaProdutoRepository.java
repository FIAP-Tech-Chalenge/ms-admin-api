package com.fiap.msadminapi.infra.adpter.repository.produto;

import com.fiap.msadminapi.domain.gateway.produto.DeletarProdutoInterface;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DeletaProdutoRepository implements DeletarProdutoInterface {

    private final ProdutoRepository produtoRepository;

    @Override
    public void deletaProduto(UUID uuid) {
        ProdutoModel produtoModel = this.produtoRepository.findByUuid(uuid);
        this.produtoRepository.delete(produtoModel);
    }
}