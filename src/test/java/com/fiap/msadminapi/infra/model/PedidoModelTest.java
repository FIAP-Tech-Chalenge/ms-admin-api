package com.fiap.msadminapi.infra.model;

import com.fiap.msadminapi.domain.enums.pedido.StatusPagamento;
import com.fiap.msadminapi.domain.enums.pedido.StatusPedido;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PedidoModelTest {

    @Test
    void deveCriarUmModeloDePedidoComDadosValidos() {
        var uuid = UUID.randomUUID();
        var numeroPedido = Long.parseLong("12");
        var clienteId = UUID.randomUUID();
        var dataCriacao = new Date(2024, 6, 3);
        var statusPedidos = StatusPedido.RECEBIDO;
        var statusPagamento = StatusPagamento.AGUARDANDO_PAGAMENTO;
        var valorTotal = Float.parseFloat("49.9");

        var model = new PedidoModel(
                uuid,
                numeroPedido,
                clienteId,
                dataCriacao,
                statusPedidos,
                statusPagamento,
                valorTotal
        );

        assertThat(model).satisfies(m -> {
            assertThat(m.getUuid()).isEqualTo(uuid);
            assertThat(m.getNumeroPedido()).isEqualTo(numeroPedido);
            assertThat(m.getClienteId()).isEqualTo(clienteId);
            assertThat(m.getDataCriacao()).isEqualTo(dataCriacao);
            assertThat(m.getStatusPedido()).isEqualTo(statusPedidos);
            assertThat(m.getStatusPagamento()).isEqualTo(statusPagamento);
            assertThat(m.getValorTotal()).isEqualTo(valorTotal);
        });
    }

    @Test
    void devePermitirAlterarUmModeloDePedidoComDadosValidos() {
        var uuid = UUID.randomUUID();
        var numeroPedido = Long.parseLong("12");
        var clienteId = UUID.randomUUID();
        var dataCriacao = new Date(2024, 6, 3);
        var statusPedidos = StatusPedido.RECEBIDO;
        var statusPagamento = StatusPagamento.AGUARDANDO_PAGAMENTO;
        var valorTotal = Float.parseFloat("49.9");

        var model = new PedidoModel(
                UUID.randomUUID(),
                Long.parseLong("12"),
                UUID.randomUUID(),
                new Date(2024, 6, 3),
                StatusPedido.RECEBIDO,
                StatusPagamento.AGUARDANDO_PAGAMENTO,
                Float.parseFloat("49.9")
        );

        model.setUuid(uuid);
        model.setNumeroPedido(numeroPedido);
        model.setClienteId(clienteId);
        model.setDataCriacao(dataCriacao);
        model.setStatusPedido(statusPedidos);
        model.setStatusPagamento(statusPagamento);
        model.setValorTotal(valorTotal);

        assertThat(model).satisfies(m -> {
            assertThat(m.getUuid()).isEqualTo(uuid);
            assertThat(m.getNumeroPedido()).isEqualTo(numeroPedido);
            assertThat(m.getClienteId()).isEqualTo(clienteId);
            assertThat(m.getDataCriacao()).isEqualTo(dataCriacao);
            assertThat(m.getStatusPedido()).isEqualTo(statusPedidos);
            assertThat(m.getStatusPagamento()).isEqualTo(statusPagamento);
            assertThat(m.getValorTotal()).isEqualTo(valorTotal);
        });
    }

    @Test
    void devePermitirCriarUmModeloDePedidoComDadosValidosSemArgumentosNoConstructor() {
        var uuid = UUID.randomUUID();
        var numeroPedido = Long.parseLong("12");
        var clienteId = UUID.randomUUID();
        var dataCriacao = new Date(2024, 6, 3);
        var statusPedidos = StatusPedido.RECEBIDO;
        var statusPagamento = StatusPagamento.AGUARDANDO_PAGAMENTO;
        var valorTotal = Float.parseFloat("49.9");

        var model = new PedidoModel();

        model.setUuid(uuid);
        model.setNumeroPedido(numeroPedido);
        model.setClienteId(clienteId);
        model.setDataCriacao(dataCriacao);
        model.setStatusPedido(statusPedidos);
        model.setStatusPagamento(statusPagamento);
        model.setValorTotal(valorTotal);

        assertThat(model).satisfies(m -> {
            assertThat(m.getUuid()).isEqualTo(uuid);
            assertThat(m.getNumeroPedido()).isEqualTo(numeroPedido);
            assertThat(m.getClienteId()).isEqualTo(clienteId);
            assertThat(m.getDataCriacao()).isEqualTo(dataCriacao);
            assertThat(m.getStatusPedido()).isEqualTo(statusPedidos);
            assertThat(m.getStatusPagamento()).isEqualTo(statusPagamento);
            assertThat(m.getValorTotal()).isEqualTo(valorTotal);
        });
    }

}
