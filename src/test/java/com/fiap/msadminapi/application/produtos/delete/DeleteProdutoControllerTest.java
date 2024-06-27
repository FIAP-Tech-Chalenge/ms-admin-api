package com.fiap.msadminapi.application.produtos.delete;

import com.fiap.msadminapi.application.produtos.categoria.get.GetProdutoController;
import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.gateway.produto.BuscaProdutoInterface;
import com.fiap.msadminapi.domain.gateway.produto.DeletarProdutoInterface;
import com.fiap.msadminapi.domain.generic.output.OutputError;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import com.fiap.msadminapi.domain.useCase.produto.BuscaProdutoPorUuidUseCase;
import com.fiap.msadminapi.domain.useCase.produto.DeletaProdutoUseCase;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class DeleteProdutoControllerTest {

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    DeletaProdutoUseCase useCase;

    @Mock
    DeletarProdutoInterface deletarProdutoInterface;

    @Mock
    BuscaProdutoInterface buscaProdutoInterface;

    DeleteProdutoController controller;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new DeletaProdutoUseCase(deletarProdutoInterface, buscaProdutoInterface);
        controller = new DeleteProdutoController(produtoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveGerarExcecao_QuandoRetornarOPresenterCorreto_StatusCodeDiferenteDeDuzentosEQuatro() {
        var uuid = UUID.randomUUID();
        try {
            when(buscaProdutoInterface.encontraProdutoPorUuid(uuid))
                    .thenThrow(HttpClientErrorException.NotFound.class);

            var presenter = controller.deletaProduto(uuid);
            assertThat(presenter.getStatusCode().toString()).isEqualTo("404 NOT_FOUND");
        } catch (Exception e) {}
    }
}
