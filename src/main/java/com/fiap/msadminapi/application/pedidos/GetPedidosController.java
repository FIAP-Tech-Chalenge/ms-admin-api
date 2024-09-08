package com.fiap.msadminapi.application.pedidos;

import com.fiap.msadminapi.application.response.GenericResponse;
import com.fiap.msadminapi.application.response.PresenterResponse;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.output.pedido.BuscaTodosPedidoOutput;
import com.fiap.msadminapi.domain.presenters.cliente.pedido.GetPedidosPresenter;
import com.fiap.msadminapi.domain.useCase.pedido.BuscaTodosPedidosUseCase;
import com.fiap.msadminapi.infra.adpter.repository.pedido.BuscarPedidoRepository;
import com.fiap.msadminapi.infra.repository.PedidoProdutoRepository;
import com.fiap.msadminapi.infra.repository.PedidoRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/pedido")
public class GetPedidosController {

    private final PedidoRepository pedidoRepository;
    private final PedidoProdutoRepository pedidoProdutoRepository;

    @GetMapping("/todos-pedidos")
    @Operation(summary = "Listar pedidos", tags = {"admin"})
    public ResponseEntity<Object> getAllPedidos() {
        BuscaTodosPedidosUseCase useCase = new BuscaTodosPedidosUseCase(new BuscarPedidoRepository(pedidoRepository, pedidoProdutoRepository));
        useCase.execute();
        OutputInterface outputInterface = useCase.getBuscaProdutoOutput();

        if (outputInterface.getOutputStatus().getCode() != 200) {
            return new GenericResponse().response(outputInterface);
        }

        GetPedidosPresenter presenter = new GetPedidosPresenter((BuscaTodosPedidoOutput) outputInterface);
        return new PresenterResponse().response(presenter);
    }
}


