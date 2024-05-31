package com.fiap.msadminapi.application.controllers.admin.produtos.update;


import com.fiap.msadminapi.application.controllers.admin.produtos.update.requests.UpdateProdutoRequest;
import com.fiap.msadminapi.application.response.GenericResponse;
import com.fiap.msadminapi.application.response.PresenterResponse;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.input.produto.EditaProdutoInput;
import com.fiap.msadminapi.domain.output.produto.EditaProdutoOutput;
import com.fiap.msadminapi.domain.presenters.cliente.produto.UpdateProdutoPresenter;
import com.fiap.msadminapi.domain.useCase.produto.EditaProdutoUseCase;
import com.fiap.msadminapi.infra.adpter.repository.produto.BuscarProdutoRepository;
import com.fiap.msadminapi.infra.adpter.repository.produto.EditaProdutoRepository;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/produto")
public class UpdateProdutoController {
    private final ProdutoRepository produtoRepository;

    @PutMapping("/{uuid}")
    @Operation(tags = {"admin"})
    public ResponseEntity<Object> editaProduto(@PathVariable UUID uuid, @RequestBody UpdateProdutoRequest produtoRequest) {
        EditaProdutoInput produtoInput = new EditaProdutoInput(
                produtoRequest.nome(),
                produtoRequest.valor(),
                produtoRequest.descricao(),
                produtoRequest.categoria(),
                produtoRequest.quantidade(),
                produtoRequest.dataCriacao()
        );
        EditaProdutoUseCase useCase = new EditaProdutoUseCase(
                new EditaProdutoRepository(produtoRepository),
                new BuscarProdutoRepository(produtoRepository)
        );
        useCase.execute(produtoInput, uuid);
        OutputInterface outputInterface = useCase.getEditaProdutoOutput();
        if (outputInterface.getOutputStatus().getCode() != 201) {
            return new GenericResponse().response(outputInterface);
        }

        UpdateProdutoPresenter presenter = new UpdateProdutoPresenter((EditaProdutoOutput) outputInterface);
        return new PresenterResponse().response(presenter);
    }
}
