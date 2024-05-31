package com.fiap.msadminapi.domain.useCase.cliente;

import com.fiap.msadminapi.domain.entity.cliente.Cliente;
import com.fiap.msadminapi.domain.exception.pedido.ClienteNaoEncontradoException;
import com.fiap.msadminapi.domain.gateway.cliente.ClienteInterface;
import com.fiap.msadminapi.domain.generic.output.OutputError;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import com.fiap.msadminapi.domain.output.cliente.ClienteOutput;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class GetClienteByUuidUseCase {

    private final ClienteInterface clienteInterface;
    private OutputInterface outputInterface;

    public void execute(UUID uuid) throws Exception {
        try {
            Cliente clienteBusca = this.clienteInterface.getClienteByUuid(uuid);
            if (clienteBusca == null) {
                throw new ClienteNaoEncontradoException("Cliente não encontrado");
            }

            this.outputInterface = new ClienteOutput(
                    clienteBusca,
                    new OutputStatus(200, "Ok", "Cliente encontrado")
            );

        } catch (ClienteNaoEncontradoException naoEncontradoException) {
            this.outputInterface = new OutputError(
                    naoEncontradoException.getMessage(),
                    new OutputStatus(404, "Not found", naoEncontradoException.getMessage())
            );
        } catch (Exception e) {
            this.outputInterface = new OutputError(
                    e.getMessage(),
                    new OutputStatus(500, "Internal Error", e.getMessage())
            );
        }
    }
}
