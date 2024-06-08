package com.fiap.msadminapi.domain.output.produto;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BuscaTodosProdutoOutputTest {

    BuscaTodosProdutoOutput output;
    OutputStatus outputStatus;

    @BeforeEach
    public void setup() {
        outputStatus = new OutputStatus(200, "OK", "Cliente encontrado");
    }

    @Test
    void deveRetornarOProdutoNoBody() {
        var produto = new Produto("Produto 1", Float.parseFloat("17.9"), "", CategoriaEnum.LANCHE, 1);
        var listProdutos = List.of(produto);

        output = new BuscaTodosProdutoOutput(listProdutos, outputStatus);

        assertThat(output.getBody()).isEqualTo(listProdutos);
        assertThat(output.getListProdutos()).isEqualTo(listProdutos);
    }

    @Test
    void deveRetornarOPedidoAdicionadoAposCriacaoSemArgumentos() {
        var produto = new Produto("Produto 1", Float.parseFloat("17.9"), "", CategoriaEnum.LANCHE, 1);
        var listProdutos = List.of(produto);

        output = new BuscaTodosProdutoOutput();
        output.setListProdutos(listProdutos);
        output.setOutputStatus(outputStatus);

        assertThat(output.getBody()).isEqualTo(listProdutos);
        assertThat(output.getListProdutos()).isEqualTo(listProdutos);
    }

}
