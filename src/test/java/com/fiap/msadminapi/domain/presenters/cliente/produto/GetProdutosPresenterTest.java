package com.fiap.msadminapi.domain.presenters.cliente.produto;

import com.fiap.msadminapi.domain.entity.produto.Imagem;
import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.output.produto.BuscaTodosProdutoOutput;
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

public class GetProdutosPresenterTest {

    @Mock
    BuscaTodosProdutoOutput output;

    GetProdutosPresenter presenter;

    AutoCloseable openMocks;

    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        presenter = new GetProdutosPresenter(output);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirRetornarUmOutputCorreto() {
        var outputRetornado = presenter.getOutput();
        assertThat(outputRetornado).isInstanceOf(BuscaTodosProdutoOutput.class);
    }

    @Test
    void devePermitirRetornarOArrayCorreto() {
        var uuid = UUID.randomUUID();
        var quantidade = 1;
        var categoria = CategoriaEnum.LANCHE;
        var valor = Float.parseFloat("17.9");
        var descricao = "";
        var nome = "Produto 1";
        var produto = new Produto(nome, valor, descricao, categoria, quantidade, List.of());
        produto.setUuid(uuid);

        var produtoArray = new HashMap<>();
        produtoArray.put("uuid", uuid.toString());
        produtoArray.put("nome", nome);
        produtoArray.put("descricao", descricao);
        produtoArray.put("categoria", categoria);
        produtoArray.put("quantidade", quantidade);
        produtoArray.put("valor", valor);
        produtoArray.put("imagens", List.of());

        var listaProdutosArray = new HashMap<>();
        listaProdutosArray.put("produtos", List.of(produtoArray));

        when(output.getListProdutos()).thenReturn(List.of(produto));

        var produtosRetornados = presenter.toArray();
        assertThat(produtosRetornados).isEqualTo(listaProdutosArray);
    }

}
