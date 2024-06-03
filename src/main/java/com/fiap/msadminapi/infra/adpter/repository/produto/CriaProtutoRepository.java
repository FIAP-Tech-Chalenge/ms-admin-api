package com.fiap.msadminapi.infra.adpter.repository.produto;

import com.fiap.msadminapi.domain.entity.produto.Imagem;
import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.gateway.produto.CriarProdutoInterface;
import com.fiap.msadminapi.infra.model.ProdutoImagemModel;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoImagensRepository;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
public class CriaProtutoRepository implements CriarProdutoInterface {

    private final ProdutoRepository produtoRepository;
    private final ProdutoImagensRepository produtoImagemRepository;

    private static List<ProdutoImagemModel> getProdutoImagemModels(Produto produto) {
        List<ProdutoImagemModel> produtoImagens = new ArrayList<>();
        for (Imagem imagemEntity : produto.getImagens()) {
            String nome = imagemEntity.nome();
            String url = imagemEntity.url();
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
        var uuid = UUID.randomUUID();
        ProdutoModel produtoModel = new ProdutoModel(
                uuid,
                produto.getNome(),
                produto.getValor(),
                produto.getDescricao(),
                produto.getCategoria(),
                produto.getQuantidade()
        );
        this.produtoRepository.save(produtoModel);
        if (produto.getImagens() != null && !produto.getImagens().isEmpty()) {
            List<ProdutoImagemModel> produtoImagens = getProdutoImagemModels(produto);
            produtoImagemRepository.saveAll(produtoImagens);
            produto.setImagens(produto.getImagens());
        }
        return produto;
    }

}