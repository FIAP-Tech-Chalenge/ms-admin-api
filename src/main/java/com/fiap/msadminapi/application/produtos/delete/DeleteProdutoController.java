package com.fiap.msadminapi.application.produtos.delete;


import com.fiap.msadminapi.application.response.GenericResponse;
import com.fiap.msadminapi.application.response.PresenterResponse;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.output.produto.DeletaProdutoOutput;
import com.fiap.msadminapi.domain.presenters.cliente.produto.DeleteProdutoPresenter;
import com.fiap.msadminapi.domain.useCase.produto.DeletaProdutoUseCase;
import com.fiap.msadminapi.infra.adpter.repository.produto.BuscarProdutoRepository;
import com.fiap.msadminapi.infra.adpter.repository.produto.DeletaProdutoRepository;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/produto")
public class DeleteProdutoController {
    private final ProdutoRepository produtoRepository;

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Excluir produto", tags = {"admin"})
    @Transactional
    public ResponseEntity<Object> deletaProduto(@PathVariable UUID uuid) {
        DeletaProdutoUseCase useCase = new DeletaProdutoUseCase(
                new DeletaProdutoRepository(produtoRepository),
                new BuscarProdutoRepository(produtoRepository)
        );
        useCase.execute(uuid);
        OutputInterface outputInterface = useCase.getDeletaProdutoOutput();
        if (outputInterface.getOutputStatus().getCode() != 204) {
            return new GenericResponse().response(outputInterface);
        }

        DeleteProdutoPresenter presenter = new DeleteProdutoPresenter((DeletaProdutoOutput) outputInterface);
        return new PresenterResponse().response(presenter);
    }
}
