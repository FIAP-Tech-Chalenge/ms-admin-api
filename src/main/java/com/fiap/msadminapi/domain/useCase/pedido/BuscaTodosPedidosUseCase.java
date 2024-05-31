package com.fiap.msadminapi.domain.useCase.pedido;

import com.fiap.msadminapi.domain.entity.pedido.Pedido;
import com.fiap.msadminapi.domain.gateway.pedido.BuscaPedidoInterface;
import com.fiap.msadminapi.domain.generic.output.OutputError;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import com.fiap.msadminapi.domain.output.pedido.BuscaTodosPedidoOutput;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class BuscaTodosPedidosUseCase {

    private final BuscaPedidoInterface buscaPedidoInterface;
    private OutputInterface buscaProdutoOutput;

    public void execute() {
        try {
            List<Pedido> listPedidos = buscaPedidoInterface.findAll();

            buscaProdutoOutput = new BuscaTodosPedidoOutput(
                    listPedidos,
                    new OutputStatus(200, "OK", "Lista de pedidos")
            );

        } catch (Exception e) {
            buscaProdutoOutput = new OutputError(
                    e.getMessage(),
                    new OutputStatus(500, "Internal Server Error", "Erro no servidor")
            );
        }
    }

}

