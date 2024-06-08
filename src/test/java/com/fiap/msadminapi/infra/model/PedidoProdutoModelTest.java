package com.fiap.msadminapi.infra.model;

import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PedidoProdutoModelTest {

    @Test
    void deveCriarUmModeloDePedidoComDadosValidos() {
        var id = Long.parseLong("123456789");
        var valor = Float.parseFloat("10");
        var quantidade = 100;
        var categoria = CategoriaEnum.LANCHE;
        var uuidProduto = UUID.randomUUID();
        var uuidPedido = UUID.randomUUID();

        var model = new PedidoProdutoModel(
                id,
                valor,
                quantidade,
                categoria,
                uuidProduto,
                uuidPedido
        );

        assertThat(model).satisfies(m -> {
            assertThat(m.getId()).isEqualTo(id);
            assertThat(m.getValor()).isEqualTo(valor);
            assertThat(m.getQuantidade()).isEqualTo(quantidade);
            assertThat(m.getCategoria()).isEqualTo(categoria);
            assertThat(m.getProdutoUuid()).isEqualTo(uuidProduto);
            assertThat(m.getPedidoUuid()).isEqualTo(uuidPedido);
        });
    }

    @Test
    void devePermitirAlterarUmModeloDePedidoComDadosValidos() {
        var id = Long.parseLong("123456789");
        var valor = Float.parseFloat("10");
        var quantidade = 100;
        var categoria = CategoriaEnum.LANCHE;
        var uuidProduto = UUID.randomUUID();
        var uuidPedido = UUID.randomUUID();

        var model = new PedidoProdutoModel(
                Long.parseLong("14321231"),
                Float.parseFloat("15"),
                150,
                CategoriaEnum.BEBIDA,
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        model.setId(id);
        model.setValor(valor);
        model.setQuantidade(quantidade);
        model.setCategoria(categoria);
        model.setProdutoUuid(uuidProduto);
        model.setPedidoUuid(uuidPedido);

        assertThat(model).satisfies(m -> {
            assertThat(m.getId()).isEqualTo(id);
            assertThat(m.getValor()).isEqualTo(valor);
            assertThat(m.getQuantidade()).isEqualTo(quantidade);
            assertThat(m.getCategoria()).isEqualTo(categoria);
            assertThat(m.getProdutoUuid()).isEqualTo(uuidProduto);
            assertThat(m.getPedidoUuid()).isEqualTo(uuidPedido);
        });
    }

    @Test
    void devePermitirCriarUmModeloDePedidoComDadosValidosSemArgumentosNoConstructor() {
        var id = Long.parseLong("123456789");
        var valor = Float.parseFloat("10");
        var quantidade = 100;
        var categoria = CategoriaEnum.LANCHE;
        var uuidProduto = UUID.randomUUID();
        var uuidPedido = UUID.randomUUID();

        var model = new PedidoProdutoModel();

        model.setId(id);
        model.setValor(valor);
        model.setQuantidade(quantidade);
        model.setCategoria(categoria);
        model.setProdutoUuid(uuidProduto);
        model.setPedidoUuid(uuidPedido);

        assertThat(model).satisfies(m -> {
            assertThat(m.getId()).isEqualTo(id);
            assertThat(m.getValor()).isEqualTo(valor);
            assertThat(m.getQuantidade()).isEqualTo(quantidade);
            assertThat(m.getCategoria()).isEqualTo(categoria);
            assertThat(m.getProdutoUuid()).isEqualTo(uuidProduto);
            assertThat(m.getPedidoUuid()).isEqualTo(uuidPedido);
        });
    }

}
