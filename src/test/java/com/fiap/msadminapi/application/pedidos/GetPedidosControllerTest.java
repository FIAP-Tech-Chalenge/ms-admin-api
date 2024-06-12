package com.fiap.msadminapi.application.pedidos;

import com.fiap.msadminapi.application.response.GenericResponse;
import com.fiap.msadminapi.domain.gateway.pedido.BuscaPedidoInterface;
import com.fiap.msadminapi.domain.useCase.pedido.BuscaPedidoPorUuidUseCase;
import com.fiap.msadminapi.domain.useCase.pedido.BuscaTodosPedidosUseCase;
import com.fiap.msadminapi.infra.model.PedidoModel;
import com.fiap.msadminapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msadminapi.infra.repository.PedidoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetPedidosControllerTest {

    @Mock
    PedidoRepository pedidoRepository;

    @Mock
    PedidoProdutoRepository pedidoProdutoRepository;

    @Mock
    BuscaTodosPedidosUseCase useCase;

    BuscaPedidoInterface buscaPedidoInterface;

    GetPedidosController controller;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new BuscaTodosPedidosUseCase(buscaPedidoInterface);
        controller = new GetPedidosController(pedidoRepository, pedidoProdutoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornarOPresenterCorreto() {
        var pedidos = new HashMap<>();
        pedidos.put("pedidos", List.of());

        var presenter = controller.getAllPedidos();

        assertThat(presenter.getBody()).isEqualTo(pedidos);
    }

    @Test
    void deveGerarExcecao_QuandoRetornarOPresenterCorreto_StatusCodeDiferenteDeDuzentos() {
        when(pedidoRepository.findAll())
                .thenThrow(HttpServerErrorException.InternalServerError.class);

        var presenter = controller.getAllPedidos();
        assertThat(presenter.getStatusCode().toString()).isEqualTo("500 INTERNAL_SERVER_ERROR");
        assertThat(presenter.getStatusCode()).isInstanceOf(HttpStatus.class);
    }

}
