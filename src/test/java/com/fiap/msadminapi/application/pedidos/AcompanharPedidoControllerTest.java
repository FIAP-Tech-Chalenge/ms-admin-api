package com.fiap.msadminapi.application.pedidos;

import com.fiap.msadminapi.domain.entity.pedido.Pedido;
import com.fiap.msadminapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msadminapi.domain.enums.pedido.StatusPedido;
import com.fiap.msadminapi.domain.gateway.pedido.BuscaPedidoInterface;
import com.fiap.msadminapi.domain.useCase.pedido.BuscaPedidoPorUuidUseCase;
import com.fiap.msadminapi.infra.model.PedidoModel;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msadminapi.infra.repository.PedidoRepository;
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

public class AcompanharPedidoControllerTest {

    @Mock
    PedidoRepository pedidoRepository;

    @Mock
    PedidoProdutoRepository pedidoProdutoRepository;

    @Mock
    BuscaPedidoPorUuidUseCase useCase;

    BuscaPedidoInterface buscaPedidoInterface;

    AcompanharPedidoController controller;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new BuscaPedidoPorUuidUseCase(buscaPedidoInterface);
        controller = new AcompanharPedidoController(pedidoRepository, pedidoProdutoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornarOPresenterCorreto() {
        var uuid = UUID.randomUUID();
        var clienteUuid = UUID.randomUUID();
        var pedidoModel = new PedidoModel();
        pedidoModel.setUuid(uuid);
        pedidoModel.setClienteId(clienteUuid);
        var pedidoArray = new HashMap<>();
        pedidoArray.put("uuid_pedido", uuid);
        pedidoArray.put("cliente_uuid", clienteUuid);
        pedidoArray.put("numero_pedido", null);
        pedidoArray.put("produtos", List.of());
        pedidoArray.put("status_pagamento", null);
        pedidoArray.put("status_pedido", null);
        pedidoArray.put("total", null);
        when(pedidoRepository.findByUuid(uuid)).thenReturn(pedidoModel);

        var presenter = controller.getPedido(uuid);

        assertThat(presenter.getBody()).isEqualTo(pedidoArray);
    }

    @Test
    void deveGerarExcecao_QuandoRetornarOPresenterCorreto_StatusCodeDiferenteDeDuzentos() {
        var presenter = controller.getPedido(UUID.randomUUID());
        assertThat(presenter.getStatusCode().toString()).isEqualTo("404 NOT_FOUND");
    }

}
