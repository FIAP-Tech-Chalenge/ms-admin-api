package com.fiap.msadminapi.infra.adpter.repository.produto;

import com.fiap.msadminapi.domain.entity.produto.Imagem;
import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.exception.produto.ProdutoNaoEncontradoException;
import com.fiap.msadminapi.domain.gateway.produto.BuscaProdutoInterface;
import com.fiap.msadminapi.infra.model.ImagemModel;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BuscarProdutoRepository implements BuscaProdutoInterface {

    private final ProdutoRepository produtoRepository;

    @Override
    public Produto encontraProdutoPorUuid(UUID uuid) throws ProdutoNaoEncontradoException {
        ProdutoModel produtoModel = this.produtoRepository.findByUuid(uuid);
        if (produtoModel == null) {
            throw new ProdutoNaoEncontradoException("Produto não encontrado");
        }
        List<Imagem> listImages = produtoModel.getImagens().stream()
                .map( imagem -> new Imagem(imagem.getId(), imagem.getNome(), imagem.getUrl()))
                .collect(Collectors.toList());
        Produto produtoEntity = new Produto(produtoModel.getNome(), produtoModel.getValor(), produtoModel.getDescricao(), produtoModel.getCategoria(), produtoModel.getQuantidade(), listImages);
        produtoEntity.setUuid(produtoModel.getUuid());
        return produtoEntity;
    }

    @Override
    public List<Produto> findAll() {
        List<ProdutoModel> produtosModels = produtoRepository.findAll();
        return getProdutos(produtosModels);
    }

    @Override
    public List<Produto> encontraProdutoPorCategoria(CategoriaEnum categoria) throws ProdutoNaoEncontradoException {
        List<ProdutoModel> produtosModel = this.produtoRepository.findByCategoria(categoria);
        if (produtosModel.isEmpty()) {
            throw new ProdutoNaoEncontradoException("Produto não encontrado");
        }
        return getProdutos(produtosModel);
    }

    private List<Produto> getProdutos(List<ProdutoModel> produtosModel) {
        List<Produto> produtosEntity = new ArrayList<>();
        for (ProdutoModel produtoModel : produtosModel) {
            List<Imagem> listImages = produtoModel.getImagens().stream()
                    .map(imagem -> new Imagem(imagem.getId(), imagem.getNome(), imagem.getUrl()))
                    .collect(Collectors.toList());
            Produto produtoEntity = new Produto(produtoModel.getNome(), produtoModel.getValor(), produtoModel.getDescricao(), produtoModel.getCategoria(), produtoModel.getQuantidade(), listImages);
            produtoEntity.setUuid(produtoModel.getUuid());
            produtosEntity.add(produtoEntity);
        }
        return produtosEntity;
    }
}