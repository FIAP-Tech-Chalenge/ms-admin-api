package com.fiap.msadminapi.application.produtos.categoria.get;


import com.fiap.msadminapi.application.response.GenericResponse;
import com.fiap.msadminapi.application.response.PresenterResponse;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.output.produto.BuscaProdutoOutput;
import com.fiap.msadminapi.domain.presenters.cliente.produto.GetProdutoPresenter;
import com.fiap.msadminapi.domain.useCase.produto.BuscaProdutoPorUuidUseCase;
import com.fiap.msadminapi.infra.adpter.repository.produto.BuscarProdutoRepository;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
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
@RequestMapping("admin/produto")
public class GetProdutoController {
    private final ProdutoRepository produtoRepository;

    @GetMapping("/{uuid}")
    @Operation(summary = "Listar um produto pelo UUID", tags = {"admin"})
    public ResponseEntity<Object> getProduto(@PathVariable UUID uuid) {
        BuscaProdutoPorUuidUseCase useCase = new BuscaProdutoPorUuidUseCase(new BuscarProdutoRepository(produtoRepository));
        useCase.execute(uuid);
        OutputInterface outputInterface = useCase.getBuscaProdutoOutput();
        if (outputInterface.getOutputStatus().getCode() != 200) {
            return new GenericResponse().response(outputInterface);
        }

        GetProdutoPresenter presenter = new GetProdutoPresenter((BuscaProdutoOutput) outputInterface);
        return new PresenterResponse().response(presenter);
    }
}
