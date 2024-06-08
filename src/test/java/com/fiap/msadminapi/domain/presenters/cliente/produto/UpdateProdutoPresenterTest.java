package com.fiap.msadminapi.domain.presenters.cliente.produto;

import com.fiap.msadminapi.domain.entity.produto.Imagem;
import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.output.produto.EditaProdutoOutput;
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

public class UpdateProdutoPresenterTest {

    @Mock
    EditaProdutoOutput output;

    UpdateProdutoPresenter presenter;

    AutoCloseable openMocks;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        presenter = new UpdateProdutoPresenter(output);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornarOOutputDeEditaProdutoCorretamente() {
        assertThat(presenter.getOutput()).isInstanceOf(EditaProdutoOutput.class);
    }

    @Test
    void deveRetornarOArrayDeEditaProdutoCorretamente() {
        var uuid = UUID.randomUUID();
        var nome = "Produto 1";
        var valor = Float.parseFloat("17.9");
        var descricao = "";
        var quantidade = 1;
        var categoria = CategoriaEnum.LANCHE;
        var produto = new Produto(nome, valor, descricao, categoria, quantidade);
        produto.setUuid(uuid);

        var produtoArray = new HashMap<>();
        produtoArray.put("uuid", uuid);
        produtoArray.put("nome", nome);
        produtoArray.put("valor", valor);
        produtoArray.put("descricao", descricao);
        produtoArray.put("categoria", categoria);
        produtoArray.put("quantidade", quantidade);

        when(output.getProduto()).thenReturn(produto);

        var produtoRetornado = presenter.toArray();

        assertThat(produtoRetornado).isEqualTo(produtoArray);
    }
}
