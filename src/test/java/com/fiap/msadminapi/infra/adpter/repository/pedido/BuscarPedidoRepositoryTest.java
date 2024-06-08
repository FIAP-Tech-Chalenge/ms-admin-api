package com.fiap.msadminapi.infra.adpter.repository.pedido;

import com.fiap.msadminapi.domain.entity.pedido.Pedido;
import com.fiap.msadminapi.infra.model.PedidoModel;
import com.fiap.msadminapi.infra.model.PedidoProdutoModel;
import com.fiap.msadminapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msadminapi.infra.repository.PedidoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BuscarPedidoRepositoryTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoProdutoRepository pedidoProdutoRepository;

    private BuscarPedidoRepository buscarPedidoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        buscarPedidoRepository = new BuscarPedidoRepository(pedidoRepository, pedidoProdutoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirBuscarPorTodosOsPedidos() {
        var pedidoUuid = UUID.randomUUID();
        var clienteUuid = UUID.randomUUID();
        var pedido = new PedidoModel();
        pedido.setUuid(pedidoUuid);
        pedido.setClienteId(clienteUuid);
        pedido.setNumeroPedido(Long.parseLong("123"));
        var listaPedido = List.of(pedido);

        var pedidoProduto = new PedidoProdutoModel();
        pedidoProduto.setPedidoUuid(UUID.randomUUID());
        var listaPedidoProduto = List.of(pedidoProduto);

        var pedidoEntity = new Pedido(clienteUuid);
        pedidoEntity.setUuid(pedidoUuid);
        pedidoEntity.setNumeroPedido(Long.parseLong("123"));

        when(pedidoRepository.findAll()).thenReturn(listaPedido);
        when(pedidoProdutoRepository.findPedidoProdutoModelsByPedidoUuid(pedido.getUuid())).thenReturn(listaPedidoProduto);

        var pedidosEncontrados = buscarPedidoRepository.findAll();
        assertThat(pedidosEncontrados)
            .hasSize(1)
            .satisfies(p -> {
                var pedidoLista = p.getFirst();
                assertThat(pedidoLista.getUuid()).isEqualTo(pedidoEntity.getUuid());
                assertThat(pedidoLista.getClienteUuid()).isEqualTo(pedidoEntity.getClienteUuid());
            });
    }

    @Test
    void devePermitirEncontrarPedidoPorUuid() {
        var pedidoUuid = UUID.randomUUID();
        var clienteUuid = UUID.randomUUID();
        var pedido = new PedidoModel();
        pedido.setUuid(pedidoUuid);
        pedido.setClienteId(clienteUuid);
        pedido.setNumeroPedido(Long.parseLong("123"));

        var pedidoProduto = new PedidoProdutoModel();
        pedidoProduto.setPedidoUuid(UUID.randomUUID());
        var listaPedidoProduto = List.of(pedidoProduto);

        var pedidoEntity = new Pedido(clienteUuid);
        pedidoEntity.setUuid(pedidoUuid);
        pedidoEntity.setNumeroPedido(Long.parseLong("123"));

        when(pedidoRepository.findByUuid(pedidoUuid)).thenReturn(pedido);
        when(pedidoProdutoRepository.findPedidoProdutoModelsByPedidoUuid(pedidoUuid)).thenReturn(listaPedidoProduto);

        var pedidoEncontrado = buscarPedidoRepository.encontraPedidoPorUuid(pedidoUuid, clienteUuid);
        assertThat(pedidoEncontrado)
                .satisfies(p -> {
                    assertThat(p.getUuid()).isEqualTo(pedidoEntity.getUuid());
                    assertThat(p.getClienteUuid()).isEqualTo(pedidoEntity.getClienteUuid());
                });
    }

    @Test
    void deveRetornarNulo_QuandoEncontrarPedidoPorUuid_PedidoModelIgualANulo() {
        when(pedidoRepository.findByUuid(any(UUID.class))).thenReturn(null);

        var pedidoEncontrado = buscarPedidoRepository.encontraPedidoPorUuid(UUID.randomUUID(), UUID.randomUUID());

        assertThat(pedidoEncontrado).isNull();
    }

    @Test
    void deveRetornarNulo_QuandoEncontrarPedidoPorUuid_ClienteUuidIgualANulo() {
        PedidoModel pedidoModel = new PedidoModel();
        pedidoModel.setUuid(UUID.randomUUID());
        pedidoModel.setClienteId(UUID.randomUUID());

        when(pedidoRepository.findByUuid(any(UUID.class))).thenReturn(pedidoModel);

        var pedidoEncontrado = buscarPedidoRepository.encontraPedidoPorUuid(UUID.randomUUID(), null);

        assertThat(pedidoEncontrado).isNull();
    }

    @Test
    void deveRetornarNulo_QuandoEncontrarPedidoPorUuid_PedidoModelTemIdDoClienteDiferenteDoClienteIdPassadoNaFuncao() {
        var pedidoUuid = UUID.randomUUID();
        PedidoModel pedidoModel = new PedidoModel();
        pedidoModel.setUuid(pedidoUuid);
        pedidoModel.setClienteId(UUID.randomUUID());

        when(pedidoRepository.findByUuid(any(UUID.class))).thenReturn(pedidoModel);

        var pedidoEncontrado = buscarPedidoRepository.encontraPedidoPorUuid(pedidoUuid, UUID.randomUUID());

        assertThat(pedidoEncontrado).isNull();
    }
}
