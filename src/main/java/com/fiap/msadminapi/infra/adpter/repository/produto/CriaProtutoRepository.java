package com.fiap.msadminapi.infra.adpter.repository.produto;


import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.gateway.produto.CriarProdutoInterface;
import com.fiap.msadminapi.infra.model.ImagemModel;
import com.fiap.msadminapi.infra.model.ProdutoImagemModel;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoImagensRepository;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;



@RequiredArgsConstructor
public class CriaProtutoRepository implements CriarProdutoInterface {

    private final ProdutoRepository produtoRepository;
    private final ProdutoImagensRepository produtoImagemRepository;

    private static List<ProdutoImagemModel> getProdutoImagemModels(Produto produto) {
        List<ProdutoImagemModel> produtoImagens = new ArrayList<>();
        for (ImagemModel imagemEntity : produto.getImagens()) {
            String nome = imagemEntity.getNome();
            String url = imagemEntity.getUrl();
            ProdutoImagemModel produtoImagem = new ProdutoImagemModel();
            produtoImagem.setProdutoUuid(produto.getUuid());
            produtoImagem.setNome(nome);
            produtoImagem.setUrl(url);
            produtoImagens.add(produtoImagem);
        }
        return produtoImagens;
    }

    @Override
    public Produto criaProduto(Produto produto) {
        ProdutoModel produtoModel = new ProdutoModel(
                produto.getNome(),
                produto.getValor(),
                produto.getDescricao(),
                produto.getCategoria(),
                produto.getQuantidade(),
                new ArrayList<>()
        );
        this.produtoRepository.save(produtoModel);
        if (produto.getImagens() != null && !produto.getImagens().isEmpty()) {
            List<ProdutoImagemModel> produtoImagens = getProdutoImagemModels(produto);
            produtoImagemRepository.saveAll(produtoImagens);
            produto.setImagens(produto.getImagens());
        }
        produto.setUuid(produtoModel.getUuid());
        return produto;
    }

}