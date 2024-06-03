package com.fiap.msadminapi.infra.adpter.repository;

import com.fiap.msadminapi.domain.entity.produto.Imagem;
import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.exception.produto.ProdutoNaoEncontradoException;
import com.fiap.msadminapi.infra.adpter.repository.produto.CriaProtutoRepository;
import com.fiap.msadminapi.infra.adpter.repository.produto.DeletaProdutoRepository;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ClienteRepository;
import com.fiap.msadminapi.infra.repository.ProdutoImagensRepository;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CriaProtutoRepositoryTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoImagensRepository produtoImagensRepository;

    private CriaProtutoRepository criaProtutoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        criaProtutoRepository = new CriaProtutoRepository(produtoRepository, produtoImagensRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirCriarProduto() {
        var produto = new Produto("Produto 3", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 2);
        var produtoModel = new ProdutoModel(UUID.randomUUID(), "Produto 3", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
        var imagem = new Imagem("titulo", "https://imagem.com");
        var imagens = Arrays.asList(imagem, imagem);
        produto.setImagens(imagens);
        when(produtoRepository.save(produtoModel)).thenReturn(produtoModel);

        var produtoRecebido = criaProtutoRepository.criaProduto(produto);

        assertThat(produtoRecebido).isEqualTo(produto);
    }

    @Test
    void devePermitirCriarProdutoSemImagem() {
        var produto = new Produto("Produto 3", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 2);
        var produtoModel = new ProdutoModel(UUID.randomUUID(), "Produto 3", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);

        when(produtoRepository.save(produtoModel)).thenReturn(produtoModel);

        var produtoRecebido = criaProtutoRepository.criaProduto(produto);

        assertThat(produtoRecebido).isEqualTo(produto);
    }

}
