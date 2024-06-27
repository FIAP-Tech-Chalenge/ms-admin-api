package com.fiap.msadminapi.application.produtos.all;

import com.fiap.msadminapi.application.pedidos.AcompanharPedidoController;
import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.gateway.produto.BuscaProdutoInterface;
import com.fiap.msadminapi.domain.generic.output.OutputError;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import com.fiap.msadminapi.domain.output.produto.BuscaProdutoOutput;
import com.fiap.msadminapi.domain.useCase.pedido.BuscaPedidoPorUuidUseCase;
import com.fiap.msadminapi.domain.useCase.produto.BuscaTodosProdutosUseCase;
import com.fiap.msadminapi.infra.adpter.repository.produto.BuscarProdutoRepository;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetProdutosControllerTest {

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    BuscarProdutoRepository buscaProdutoRepository;

    BuscaTodosProdutosUseCase useCase;

    @Mock
    BuscaProdutoInterface buscaProdutoInterface;

    GetProdutosController controller;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new BuscaTodosProdutosUseCase(buscaProdutoRepository);
        controller = new GetProdutosController(produtoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornarOPresenterCorreto() {
        var produtos = new HashMap<>();
        produtos.put("produtos", List.of());

        when(buscaProdutoRepository.findAll()).thenReturn(List.of());

        var presenter = controller.getAllProdutos();

        assertThat(presenter.getBody()).isEqualTo(produtos);
    }

}
