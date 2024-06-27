package com.fiap.msadminapi.infra.adpter.repository.produto;

import com.fiap.msadminapi.domain.entity.produto.Imagem;
import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoImagensRepository;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

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
        var produto = new Produto("Produto 3", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 2, List.of());
        var produtoModel = new ProdutoModel("Produto 3", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100, List.of());
        var imagem = new Imagem(Long.parseLong("123"), "titulo", "https://imagem.com");
        var imagens = Arrays.asList(imagem, imagem);
        produto.setImagens(imagens);
        when(produtoRepository.save(produtoModel)).thenReturn(produtoModel);

        var produtoRecebido = criaProtutoRepository.criaProduto(produto);

        assertThat(produtoRecebido).isEqualTo(produto);
    }

    @Test
    void devePermitirCriarProdutoSemImagem() {
        var produto = new Produto("Produto 3", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 2, List.of());
        var produtoModel = new ProdutoModel("Produto 3", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100, List.of());
        produto.setImagens(Arrays.asList());
        when(produtoRepository.save(produtoModel)).thenReturn(produtoModel);

        var produtoRecebido = criaProtutoRepository.criaProduto(produto);

        assertThat(produtoRecebido).isEqualTo(produto);
    }

}
