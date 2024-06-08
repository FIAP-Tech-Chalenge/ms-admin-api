package com.fiap.msadminapi.domain.presenters.cliente.produto;

import com.fiap.msadminapi.domain.entity.produto.Imagem;
import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.output.produto.BuscaProdutoOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetProdutoPresenterTest {

    @Mock
    BuscaProdutoOutput output;

    GetProdutoPresenter presenter;

    AutoCloseable openMocks;

    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        presenter = new GetProdutoPresenter(output);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirRetornarUmOutputCorreto() {
        var outputRetornado = presenter.getOutput();
        assertThat(outputRetornado).isInstanceOf(BuscaProdutoOutput.class);
    }

    @Test
    void devePermitirRetornarOArrayCorreto() {
        var uuid = UUID.randomUUID();
        var quantidade = 1;
        var categoria = CategoriaEnum.LANCHE;
        var valor = Float.parseFloat("17.9");
        var descricao = "";
        var nome = "Produto 1";
        var produto = new Produto(nome, valor, descricao, categoria, quantidade);
        produto.setUuid(uuid);

        var imagemNome = "Imagem 1";
        var imagemUrl = "https://imagem1.com.br";
        var imagem = new Imagem(imagemNome, imagemUrl);

        produto.setImagens(List.of(imagem));

        var produtoArray = new HashMap<>();
        produtoArray.put("uuid", uuid);
        produtoArray.put("nome", nome);
        produtoArray.put("valor", valor);
        produtoArray.put("descricao", descricao);
        produtoArray.put("categoria", categoria);
        produtoArray.put("quantidade", quantidade);

        var imagemArray = new HashMap<>();
        imagemArray.put("nome", imagemNome);
        imagemArray.put("url", imagemUrl);

        produtoArray.put("imagens", List.of(imagemArray));

        when(output.getProduto()).thenReturn(produto);

        var produtoRetornado = presenter.toArray();
        assertThat(produtoRetornado).isEqualTo(produtoArray);
    }
}
