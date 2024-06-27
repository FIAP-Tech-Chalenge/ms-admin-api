package com.fiap.msadminapi.domain.entity.produto;

import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.input.produto.EditaProdutoInput;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ProdutoTest {

    @Test
    void devePermitirInstanciarProdutoESuasPropriedades() {
        var uuid = UUID.randomUUID();
        var nome = "Produto 1";
        var valor = Float.parseFloat("17.9");
        var descricao = "";
        var quantidade = 1;
        var categoria = CategoriaEnum.BEBIDA;
        var imagem = new Imagem(Long.parseLong("123"), "Imagem 1", "https://imagem1.com.br");
        var imagens = List.of(imagem);
        var produto = new Produto(nome, valor, descricao, categoria, quantidade, imagens);
        produto.setUuid(uuid);

        assertThat(produto.getUuid()).isEqualTo(uuid);
        assertThat(produto.getNome()).isEqualTo(nome);
        assertThat(produto.getValor()).isEqualTo(valor);
        assertThat(produto.getDescricao()).isEqualTo(descricao);
        assertThat(produto.getQuantidade()).isEqualTo(quantidade);
        assertThat(produto.getCategoria()).isEqualTo(categoria);
        assertThat(produto.getImagens()).isEqualTo(imagens);
    }

    @Test
    void devePermitirCriarProduto() {
        var uuid = UUID.randomUUID();
        var nome = "Produto 1";
        var valor = Float.parseFloat("17.9");
        var descricao = "";
        var quantidade = 1;
        var categoria = CategoriaEnum.BEBIDA;
        var imagem = new Imagem(Long.parseLong("123"), "Imagem 1", "https://imagem1.com.br");
        var imagens = List.of(imagem);
        var produto = new Produto(nome, valor, descricao, categoria, quantidade, imagens);
        produto.setUuid(uuid);

        try {
            var produtoCriado = produto.criaProduto();

            assertThat(produtoCriado).satisfies(p -> {
                assertThat(p.getUuid()).isEqualTo(uuid);
                assertThat(p.getNome()).isEqualTo(nome);
                assertThat(p.getValor()).isEqualTo(valor);
                assertThat(p.getDescricao()).isEqualTo(descricao);
                assertThat(p.getQuantidade()).isEqualTo(quantidade);
                assertThat(p.getCategoria()).isEqualTo(categoria);
                assertThat(p.getImagens()).isEqualTo(imagens);
            });
        } catch(Exception e) {}
    }

    @Test
    void devePermitirAtualizarProduto() {
        var uuid = UUID.randomUUID();
        var nome = "Produto 1";
        var valor = Float.parseFloat("17.9");
        var descricao = "";
        var quantidade = 1;
        var categoria = CategoriaEnum.BEBIDA;
        var imagem = new Imagem(Long.parseLong("123"), "Imagem 1", "https://imagem1.com.br");
        var imagens = List.of(imagem);
        var produto = new Produto(nome, valor, descricao, categoria, quantidade, imagens);

        var nome2 = "Produto 1";
        var valor2 = Float.parseFloat("15.9");
        var descricao2 = "Teste";
        var quantidade2 = 2;
        var categoria2 = CategoriaEnum.LANCHE;
        var imagem2 = new Imagem(Long.parseLong("567"), "Imagem 1", "https://imagem1.com.br");
        var imagens2 = List.of(imagem2);
        var editaProduto = new EditaProdutoInput(nome2, valor2, descricao2, categoria2, quantidade2, new Date(2024, 6, 24), imagens2);

        try {
            produto.atualizaProduto(editaProduto);

            assertThat(produto).satisfies(p -> {
                assertThat(p.getNome()).isEqualTo(nome2);
                assertThat(p.getValor()).isEqualTo(valor2);
                assertThat(p.getDescricao()).isEqualTo(descricao2);
                assertThat(p.getQuantidade()).isEqualTo(quantidade2);
                assertThat(p.getCategoria()).isEqualTo(categoria2);
                assertThat(p.getImagens()).isEqualTo(imagens2);
            });
        } catch(Exception e) {}
    }

}
