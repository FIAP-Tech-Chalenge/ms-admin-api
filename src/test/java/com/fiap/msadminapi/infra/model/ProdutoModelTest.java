package com.fiap.msadminapi.infra.model;

import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ProdutoModelTest {

//    @Test
//    void deveCriarUmModeloDeProdutoComDadosValidos() {
//        var uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174005");
//        var nome = "Produto 1";
//        var valor = Float.parseFloat("10");
//        var descricao = "Descricao 1";
//        var categoria = CategoriaEnum.LANCHE;
//        var quantidade = 100;
//
//        var model = new ProdutoModel(
//                uuid,
//                nome,
//                valor,
//                descricao,
//                categoria,
//                quantidade
//        );
//
//        assertThat(model).satisfies(m -> {
//            assertThat(m.getUuid()).isEqualTo(uuid);
//            assertThat(m.getNome()).isEqualTo(nome);
//            assertThat(m.getValor()).isEqualTo(valor);
//            assertThat(m.getDescricao()).isEqualTo(descricao);
//            assertThat(m.getCategoria()).isEqualTo(categoria);
//            assertThat(m.getQuantidade()).isEqualTo(quantidade);
//        });
//    }

//    @Test
//    void devePermitirAlterarUmModeloDeProdutoComDadosValidos() {
//        var uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174003");
//        var nome = "Produto 2";
//        var valor = Float.parseFloat("14");
//        var descricao = "Descricao 2";
//        var categoria = CategoriaEnum.BEBIDA;
//        var quantidade = 150;
//
//        var model = new ProdutoModel(
//                UUID.fromString("123e4567-e89b-12d3-a456-426614174005"),
//                "Produto 1",
//                Float.parseFloat("10"),
//                "Descricao 1",
//                CategoriaEnum.LANCHE,
//                100
//        );
//
//        model.setUuid(uuid);
//        model.setNome(nome);
//        model.setValor(valor);
//        model.setDescricao(descricao);
//        model.setCategoria(categoria);
//        model.setQuantidade(quantidade);
//
//        assertThat(model).satisfies(m -> {
//            assertThat(m.getUuid()).isEqualTo(uuid);
//            assertThat(m.getNome()).isEqualTo(nome);
//            assertThat(m.getValor()).isEqualTo(valor);
//            assertThat(m.getDescricao()).isEqualTo(descricao);
//            assertThat(m.getCategoria()).isEqualTo(categoria);
//            assertThat(m.getQuantidade()).isEqualTo(quantidade);
//        });
//    }

    @Test
    void devePermitirCriarUmModeloDeProdutoComDadosValidosSemArgumentosNoConstructor() {
        var uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174003");
        var nome = "Produto 2";
        var valor = Float.parseFloat("14");
        var descricao = "Descricao 2";
        var categoria = CategoriaEnum.BEBIDA;
        var quantidade = 150;

        var model = new ProdutoModel();

        model.setUuid(uuid);
        model.setNome(nome);
        model.setValor(valor);
        model.setDescricao(descricao);
        model.setCategoria(categoria);
        model.setQuantidade(quantidade);

        assertThat(model).satisfies(m -> {
            assertThat(m.getUuid()).isEqualTo(uuid);
            assertThat(m.getNome()).isEqualTo(nome);
            assertThat(m.getValor()).isEqualTo(valor);
            assertThat(m.getDescricao()).isEqualTo(descricao);
            assertThat(m.getCategoria()).isEqualTo(categoria);
            assertThat(m.getQuantidade()).isEqualTo(quantidade);
        });
    }

}
