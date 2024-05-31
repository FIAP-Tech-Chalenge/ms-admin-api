package com.fiap.msadminapi.application.controllers.admin.clientes;


import com.fiap.msadminapi.application.response.GenericResponse;
import com.fiap.msadminapi.application.response.PresenterResponse;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.output.cliente.ClienteOutput;
import com.fiap.msadminapi.domain.presenters.cliente.ClientePresenter;
import com.fiap.msadminapi.domain.useCase.cliente.GetClienteByUuidUseCase;
import com.fiap.msadminapi.infra.adpter.repository.cliente.ClienteEntityRepository;
import com.fiap.msadminapi.infra.repository.ClienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/cliente")
public class AdminGetClienteByUuidController {


    private final ClienteRepository clienteRepository;

    @GetMapping("/{uuid}")
    @Operation(tags = {"admin"})
    public ResponseEntity<Object> getClienteByUuid(@PathVariable UUID uuid) throws Exception {
        GetClienteByUuidUseCase useCase = new GetClienteByUuidUseCase(new ClienteEntityRepository(this.clienteRepository));
        useCase.execute(uuid);

        OutputInterface outputInterface = useCase.getOutputInterface();

        if (outputInterface.getOutputStatus().getCode() != 200) {
            return new GenericResponse().response(outputInterface);
        }

        ClientePresenter presenter = new ClientePresenter((ClienteOutput) outputInterface);

        return new PresenterResponse().response(presenter);
    }

}
