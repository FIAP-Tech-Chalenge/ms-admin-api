package com.fiap.msadminapi.application.produtos.categoria.get;


import com.fiap.msadminapi.application.response.GenericResponse;
import com.fiap.msadminapi.application.response.PresenterResponse;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.output.produto.BuscaTodosProdutoOutput;
import com.fiap.msadminapi.domain.presenters.cliente.produto.GetProdutosPresenter;
import com.fiap.msadminapi.domain.useCase.produto.BuscaProdutoPorCategoriaUseCase;
import com.fiap.msadminapi.infra.adpter.repository.produto.BuscarProdutoRepository;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/produto")
public class GetCategoriaProdutoController {
    private final ProdutoRepository produtoRepository;

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Listar produtos por categoria", tags = {"admin"})
    public ResponseEntity<Object> getProdutoPorCategoria(@PathVariable String categoria) {
        BuscaProdutoPorCategoriaUseCase useCase = new BuscaProdutoPorCategoriaUseCase(new BuscarProdutoRepository(produtoRepository));
        useCase.execute(categoria);
        OutputInterface outputInterface = useCase.getBuscaProdutoOutput();

        if (outputInterface.getOutputStatus().getCode() != 200) {
            return new GenericResponse().response(outputInterface);
        }

        GetProdutosPresenter presenter = new GetProdutosPresenter((BuscaTodosProdutoOutput) outputInterface);
        return new PresenterResponse().response(presenter);
    }
}
