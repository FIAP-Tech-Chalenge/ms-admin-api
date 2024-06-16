package com.fiap.msadminapi.application.produtos.categoria.get;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.gateway.produto.BuscaProdutoInterface;
import com.fiap.msadminapi.domain.useCase.produto.BuscaProdutoPorCategoriaUseCase;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetCategoriaProdutoControllerTest {

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    BuscarProdutoRepository buscaProdutoRepository;

    @Mock
    BuscaProdutoPorCategoriaUseCase useCase;

    BuscaProdutoInterface buscaProdutoInterface;

    GetCategoriaProdutoController controller;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new BuscaProdutoPorCategoriaUseCase(buscaProdutoInterface);
        controller = new GetCategoriaProdutoController(produtoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

//    @Test
//    void deveRetornarOPresenterCorreto() {
//        var categoria = CategoriaEnum.LANCHE;
//        var produtoModel = new ProdutoModel(UUID.fromString("f0989e76-ab12-4a75-ba9c-be0e3643daf"), "Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//        var listaProdutoModel = List.of(produtoModel);
//        var produtoArray = new HashMap<>();
//        produtoArray.put("categoria", CategoriaEnum.LANCHE);
//        produtoArray.put("descricao", "Descricao 1");
//        produtoArray.put("nome", "Produto 1");
//        produtoArray.put("quantidade", 100);
//        produtoArray.put("uuid", "f0989e76-ab12-4a75-ba9c-0be0e3643daf");
//        var produtosArray = new HashMap<>();
//        produtosArray.put("produtos", List.of(produtoArray));
//
//        when(produtoRepository.findByCategoria(categoria)).thenReturn(listaProdutoModel);
//
//        var presenter = controller.getProdutoPorCategoria(categoria.toString());
//
//        assertThat(presenter.getBody()).isEqualTo(produtosArray);
//    }

    @Test
    void deveGerarExcecao_QuandoRetornarOPresenterCorreto_StatusCodeDiferenteDeDuzentos() {
        var categoria = CategoriaEnum.LANCHE;
        when(produtoRepository.findByCategoria(categoria))
                .thenThrow(HttpServerErrorException.InternalServerError.class);

        var presenter = controller.getProdutoPorCategoria(categoria.toString());
        assertThat(presenter.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
