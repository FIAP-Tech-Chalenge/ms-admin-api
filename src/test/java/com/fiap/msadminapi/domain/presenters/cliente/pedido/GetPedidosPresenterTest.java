package com.fiap.msadminapi.domain.presenters.cliente.pedido;

import com.fiap.msadminapi.domain.entity.pedido.Pedido;
import com.fiap.msadminapi.domain.entity.pedido.Produto;
import com.fiap.msadminapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msadminapi.domain.enums.pedido.StatusPedido;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.output.pedido.BuscaTodosPedidoOutput;
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

public class GetPedidosPresenterTest {

    @Mock
    BuscaTodosPedidoOutput output;

    GetPedidosPresenter presenter;

    AutoCloseable openMocks;

    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        presenter = new GetPedidosPresenter(output);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornarOOutputDeListaDePedidosCorretamente() {
        var statusPagamento = StatusPagamento.PAGO;
        var numeroPedido = Long.parseLong("123");
        var clienteUuid = UUID.randomUUID();
        var statusPedido = StatusPedido.EM_PREPARACAO;
        var produto = new Produto(UUID.randomUUID(), 1, CategoriaEnum.LANCHE);
        List<Produto> produtos = List.of(produto);
        var pedido = new Pedido(clienteUuid);
        pedido.setNumeroPedido(numeroPedido);
        pedido.setProdutos(produtos);
        pedido.setStatusPedido(statusPedido);
        pedido.setStatusPagamento(statusPagamento);
        var listaPedidos = List.of(pedido);

        when(output.getListPedidos()).thenReturn(listaPedidos);

        var pedidoRetornado = presenter.getOutput().getListPedidos();
        assertThat(pedidoRetornado).satisfies(p -> {
            var pedido1 = p.getFirst();
            assertThat(pedido1.getStatusPagamento()).isEqualTo(statusPagamento);
            assertThat(pedido1.getStatusPedido()).isEqualTo(statusPedido);
            assertThat(pedido1.getProdutos()).hasSize(1).isEqualTo(produtos);
        });
    }

    @Test
    void deveRetornarUmArrayDeListaDePedidosCorretamente() {
        var uuid = UUID.randomUUID();
        var statusPagamento = StatusPagamento.PAGO;
        var numeroPedido = Long.parseLong("123");
        var total = Float.parseFloat("17.9");
        var clienteUuid = UUID.randomUUID();
        var statusPedido = StatusPedido.EM_PREPARACAO;
        var produtoUuid = UUID.randomUUID();
        var produtoQuantidade = 1;
        var produtoCategoria = CategoriaEnum.LANCHE;
        var produtoValor = Float.parseFloat("17.9");
        var produto = new Produto(produtoUuid, produtoQuantidade, produtoCategoria);
        produto.setValor(produtoValor);
        List<Produto> produtos = List.of(produto);
        var pedido = new Pedido(clienteUuid);
        pedido.setUuid(uuid);
        pedido.setNumeroPedido(numeroPedido);
        pedido.setTotal(total);
        pedido.setProdutos(produtos);
        pedido.setStatusPedido(statusPedido);
        pedido.setStatusPagamento(statusPagamento);
        var listaPedidos = List.of(pedido);

        var pedidoArray = new HashMap<>();
        pedidoArray.put("uuid", uuid.toString());
        pedidoArray.put("status_pagamento", statusPagamento.toString());
        pedidoArray.put("numero_pedido", numeroPedido);
        pedidoArray.put("total", total);
        pedidoArray.put("cliente_uuid", clienteUuid.toString());
        pedidoArray.put("status_pedido", statusPedido.toString());

        var produtoArray = new HashMap<>();
        produtoArray.put("uuid", produtoUuid.toString());
        produtoArray.put("quantidade", produtoQuantidade);
        produtoArray.put("valor", produtoValor);
        produtoArray.put("categoria", produtoCategoria.toString());

        var listaProdutosArray = List.of(produtoArray);
        pedidoArray.put("produtos", listaProdutosArray);

        var listaDePedidos = new HashMap<>();
        listaDePedidos.put("pedidos", List.of(pedidoArray));

        when(output.getListPedidos()).thenReturn(listaPedidos);

        var pedidoRetornado = presenter.toArray();
        assertThat(pedidoRetornado).isEqualTo(listaDePedidos);
    }

}
