package com.fiap.msadminapi.infra.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ImagemModelTest {

    @Test
    void devePermitirCriaUmModeloDeImagem() {
        var uuid = UUID.randomUUID();
        var produto = new ProdutoModel();
        produto.setUuid(uuid);
        var id = Long.parseLong("123");
        var nome = "Imagem 1";
        var url = "http://imagem1.com.br";
        var imagem = new ImagemModel();
        imagem.setId(id);
        imagem.setNome(nome);
        imagem.setUrl(url);
        imagem.setProduto(produto);

        assertThat(imagem.getId()).isEqualTo(id);
        assertThat(imagem.getNome()).isEqualTo(nome);
        assertThat(imagem.getUrl()).isEqualTo(url);
        assertThat(imagem.getProduto()).isEqualTo(produto);
    }

}
