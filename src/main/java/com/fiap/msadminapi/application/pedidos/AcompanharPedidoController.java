package com.fiap.msadminapi.application.pedidos;

import com.fiap.msadminapi.application.response.GenericResponse;
import com.fiap.msadminapi.application.response.PresenterResponse;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.output.pedido.BuscaPedidoOutput;
import com.fiap.msadminapi.domain.presenters.cliente.pedido.GetPedidoPresenter;
import com.fiap.msadminapi.domain.useCase.pedido.BuscaPedidoPorUuidUseCase;
import com.fiap.msadminapi.infra.adpter.repository.pedido.BuscarPedidoRepository;
import com.fiap.msadminapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msadminapi.infra.repository.PedidoRepository;
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
@RequestMapping("admin/pedido")
public class AcompanharPedidoController {

    private final PedidoRepository pedidoRepository;
    private final PedidoProdutoRepository pedidoProdutoRepository;

    @GetMapping("/{uuid}")
    @Operation(summary = "Acompanhar pedidos", tags = {"admin"})
    public ResponseEntity<Object> getPedido(@PathVariable UUID uuid) {
        BuscaPedidoPorUuidUseCase useCase = new BuscaPedidoPorUuidUseCase(new BuscarPedidoRepository(pedidoRepository, pedidoProdutoRepository));
        useCase.execute(uuid, null);
        OutputInterface outputInterface = useCase.getBuscaPedidoOutput();
        if (outputInterface.getOutputStatus().getCode() != 200) {
            return new GenericResponse().response(outputInterface);
        }

        GetPedidoPresenter presenter = new GetPedidoPresenter((BuscaPedidoOutput) outputInterface);
        return new PresenterResponse().response(presenter);
    }
}


