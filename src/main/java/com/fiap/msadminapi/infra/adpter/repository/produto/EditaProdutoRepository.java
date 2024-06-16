package com.fiap.msadminapi.infra.adpter.repository.produto;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.exception.produto.ProdutoNaoEncontradoException;
import com.fiap.msadminapi.domain.gateway.produto.EditaProdutoInterface;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;


@RequiredArgsConstructor
public class EditaProdutoRepository implements EditaProdutoInterface {

    private final ProdutoRepository produtoRepository;

    public void editaProduto(Produto produto, UUID uuid) throws ProdutoNaoEncontradoException {
        ProdutoModel produtoExistente = this.produtoRepository.findByUuid(uuid);
        if (produtoExistente == null) {
            throw new ProdutoNaoEncontradoException("Produto with UUID " + uuid + " not found.");
        }
        produtoRepository.save(new ProdutoModel(
                produto.getNome(),
                produto.getValor(),
                produto.getDescricao(),
                produto.getCategoria(),
                produto.getQuantidade(),
                produto.getImagens()));
    }

}