package com.fiap.msadminapi.infra.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ProdutoImagemModelTest {

    @Test
    void deveCriarUmModeloDeImagemDeProdutoComDadosValidos() {
        var id = Long.parseLong("123456789");
        var nome = "Imagem 1";
        var url = "https://caminho-para-imagem.com.br";
        var produtoUuid = UUID.randomUUID();

        var model = new ProdutoImagemModel(
                id,
                nome,
                url,
                produtoUuid
        );

        assertThat(model).satisfies(m -> {
           assertThat(model.getId()).isEqualTo(id);
            assertThat(model.getNome()).isEqualTo(nome);
            assertThat(model.getUrl()).isEqualTo(url);
            assertThat(model.getProdutoUuid()).isEqualTo(produtoUuid);
        });
    }

    @Test
    void devePermitirAlterarUmModeloDeImagemDeProdutoComDadosValidos() {
        var id = Long.parseLong("123456789");
        var nome = "Imagem 2";
        var url = "https://caminho-para-imagem.com.br";
        var produtoUuid = UUID.randomUUID();

        var model = new ProdutoImagemModel(
                Long.parseLong("113211312"),
                "Imagem 1",
                "https://iamgem-temporaria.com.br",
                UUID.randomUUID()
        );

        model.setId(id);
        model.setNome(nome);
        model.setUrl(url);
        model.setProdutoUuid(produtoUuid);

        assertThat(model).satisfies(m -> {
            assertThat(model.getId()).isEqualTo(id);
            assertThat(model.getNome()).isEqualTo(nome);
            assertThat(model.getUrl()).isEqualTo(url);
            assertThat(model.getProdutoUuid()).isEqualTo(produtoUuid);
        });
    }

    @Test
    void devePermitirCriarUmModeloDeImagemDeProdutoComDadosValidosSemArgumentosNoConstructor() {
        var id = Long.parseLong("123456789");
        var nome = "Imagem 2";
        var url = "https://caminho-para-imagem.com.br";
        var produtoUuid = UUID.randomUUID();

        var model = new ProdutoImagemModel();

        model.setId(id);
        model.setNome(nome);
        model.setUrl(url);
        model.setProdutoUuid(produtoUuid);

        assertThat(model).satisfies(m -> {
            assertThat(model.getId()).isEqualTo(id);
            assertThat(model.getNome()).isEqualTo(nome);
            assertThat(model.getUrl()).isEqualTo(url);
            assertThat(model.getProdutoUuid()).isEqualTo(produtoUuid);
        });
    }

}
