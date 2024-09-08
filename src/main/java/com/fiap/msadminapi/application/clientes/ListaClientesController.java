package com.fiap.msadminapi.application.clientes;

import com.fiap.msadminapi.application.response.GenericResponse;
import com.fiap.msadminapi.application.response.PresenterResponse;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.output.cliente.ListaDeClienteOutput;
import com.fiap.msadminapi.domain.presenters.cliente.lista.ListaClientesPresenter;
import com.fiap.msadminapi.domain.useCase.cliente.ListaDeClientesUseCase;
import com.fiap.msadminapi.infra.adpter.repository.cliente.ClienteEntityRepository;
import com.fiap.msadminapi.infra.repository.ClienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/clientes")
public class ListaClientesController {
    private final ClienteRepository clienteRepository;

    @GetMapping("/lista")
    @Operation(summary = "Listar todos clientes", tags = {"admin"})
    public ResponseEntity<Object> getAllPedidos() throws Exception {
        ListaDeClientesUseCase useCase = new ListaDeClientesUseCase(new ClienteEntityRepository(this.clienteRepository));
        useCase.execute();
        OutputInterface outputInterface = useCase.getOutputStatus();

        if (outputInterface.getOutputStatus().getCode() != 200) {
            return new GenericResponse().response(outputInterface);
        }

        ListaClientesPresenter presenter = new ListaClientesPresenter((ListaDeClienteOutput) outputInterface);
        return new PresenterResponse().response(presenter);
    }
}


