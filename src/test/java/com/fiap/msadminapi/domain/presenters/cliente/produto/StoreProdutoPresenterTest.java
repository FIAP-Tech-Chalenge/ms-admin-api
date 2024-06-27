package com.fiap.msadminapi.domain.presenters.cliente.produto;

import com.fiap.msadminapi.domain.entity.produto.Imagem;
import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.output.produto.CriaProdutoOutput;
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

public class StoreProdutoPresenterTest {

    @Mock
    CriaProdutoOutput output;

    StoreProdutoPresenter presenter;

    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        presenter = new StoreProdutoPresenter(output);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornarOOutputDeStoreProdutoCorretamente() {
        assertThat(presenter.getOutput()).isInstanceOf(CriaProdutoOutput.class);
    }

    @Test
    void deveRetornarOArrayDeStoreProdutoCorretamente() {
        var uuid = UUID.randomUUID();
        var nome = "Produto 1";
        var valor = Float.parseFloat("17.9");
        var descricao = "";
        var quantidade = 1;
        var categoria = CategoriaEnum.LANCHE;
        var imagemId = Long.parseLong("123");
        var imagemNome = "Imagem 1";
        var imagemUrl = "https://imagem1.com.br";
        var imagem = new Imagem(imagemId, imagemNome, imagemUrl);
        var produto = new Produto(nome, valor, descricao, categoria, quantidade, List.of(imagem));
        produto.setUuid(uuid);

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
