package com.fiap.msadminapi.domain.entity.pedido;

import com.fiap.msadminapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msadminapi.domain.enums.pedido.StatusPedido;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.exception.pedido.PedidoVazioException;
import com.fiap.msadminapi.domain.exception.pedido.ProdutoDoPedidoSemQuantidadeException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PedidoTest {

    @Test
    void devePermitirInstaciarEntidadeESuasPropriedades() {
        var clienteUuid = UUID.randomUUID();
        var uuid = UUID.randomUUID();
        var pedidoUuid = UUID.randomUUID();
        var numeroPedido = Long.parseLong("123");
        var statusPedido = StatusPedido.EM_PREPARACAO;
        var statusPagamento = StatusPagamento.PAGO;
        var total = Float.parseFloat("13.75");
        var pedido = new Pedido(clienteUuid);
        pedido.setUuid(uuid);
        pedido.setPedidoId(pedidoUuid);
        pedido.setNumeroPedido(numeroPedido);
        pedido.setStatusPedido(statusPedido);
        pedido.setStatusPagamento(statusPagamento);
        pedido.setTotal(total);

        assertThat(pedido.getClienteUuid()).isEqualTo(clienteUuid);
        assertThat(pedido.getPedidoId()).isEqualTo(pedidoUuid);
        assertThat(pedido.getUuid()).isEqualTo(uuid);
        assertThat(pedido.getNumeroPedido()).isEqualTo(numeroPedido);
        assertThat(pedido.getStatusPedido()).isEqualTo(statusPedido);
        assertThat(pedido.getStatusPagamento()).isEqualTo(statusPagamento);
        assertThat(pedido.getTotal()).isEqualTo(total);
    }

    @Test
    void devePermitirAdicionarProdutos() {
        var uuid = UUID.randomUUID();
        var quantidade = 1;
        var categoria = CategoriaEnum.LANCHE;
        var produto = new Produto(uuid, quantidade, categoria);
        var pedido = new Pedido(uuid);
        pedido.addProduto(produto);

        assertThat(pedido.getProdutos())
                .hasSize(1)
                .contains(produto);
    }

    @Test
    void deveGerarExcecao_QuandoVerificarItensDoPedido_PedidoSemQuantidade() {
        var produto1 = new Produto(UUID.randomUUID(), 1, CategoriaEnum.LANCHE);
        var produto2 = new Produto(UUID.randomUUID(), 0, CategoriaEnum.LANCHE);
        var pedido = new Pedido(UUID.randomUUID());
        pedido.addProduto(produto1);
        pedido.addProduto(produto2);

        assertThat(pedido.getProdutos())
                .hasSize(2);
        assertThatThrownBy(pedido::verificaItensDoPedido)
                .isInstanceOf(ProdutoDoPedidoSemQuantidadeException.class)
                .hasMessage("Produto com quantidade inválida");
    }

    @Test
    void deveGerarExcecao_QuandoVerificarItensDoPedido_PedidoVazio() {
        var uuid = UUID.randomUUID();
        var pedido = new Pedido(uuid);

        assertThatThrownBy(pedido::verificaItensDoPedido)
                .isInstanceOf(PedidoVazioException.class)
                .hasMessage("Pedido vazio");
    }

    @Test
    void devePermitirPegarOValorTotalDoPedido() {
        var produto1 = new Produto(UUID.randomUUID(), 1, CategoriaEnum.LANCHE);
        produto1.setValor(Float.parseFloat("17.9"));
        var produto2 = new Produto(UUID.randomUUID(), 1, CategoriaEnum.BEBIDA);
        produto2.setValor(Float.parseFloat("9.9"));
        var pedido = new Pedido(UUID.randomUUID());
        pedido.addProduto(produto1);
        pedido.addProduto(produto2);

        assertThat(pedido.valorTotalDoPedido()).isEqualTo(Float.parseFloat("27.8"));
    }
}
