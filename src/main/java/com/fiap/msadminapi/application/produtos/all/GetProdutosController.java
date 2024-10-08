package com.fiap.msadminapi.application.produtos.all;


import com.fiap.msadminapi.application.response.GenericResponse;
import com.fiap.msadminapi.application.response.PresenterResponse;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.output.produto.BuscaTodosProdutoOutput;
import com.fiap.msadminapi.domain.presenters.cliente.produto.GetProdutosPresenter;
import com.fiap.msadminapi.domain.useCase.produto.BuscaTodosProdutosUseCase;
import com.fiap.msadminapi.infra.adpter.repository.produto.BuscarProdutoRepository;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://restaurante.techchallenge.com.br", maxAge = 3600, methods = RequestMethod.GET)
@RestController
@RequiredArgsConstructor
@RequestMapping("admin/produtos")
public class GetProdutosController {
    private final ProdutoRepository produtoRepository;

    @GetMapping
    @Operation(summary = "Listar todos produtos", tags = {"admin"})
    public ResponseEntity<Object> getAllProdutos() {
        BuscaTodosProdutosUseCase useCase = new BuscaTodosProdutosUseCase(new BuscarProdutoRepository(produtoRepository));
        useCase.execute();
        OutputInterface outputInterface = useCase.getBuscaProdutoOutput();

        if (outputInterface.getOutputStatus().getCode() != 200) {
            return new GenericResponse().response(outputInterface);
        }

        GetProdutosPresenter presenter = new GetProdutosPresenter((BuscaTodosProdutoOutput) outputInterface);
        return new PresenterResponse().response(presenter);
    }
}
