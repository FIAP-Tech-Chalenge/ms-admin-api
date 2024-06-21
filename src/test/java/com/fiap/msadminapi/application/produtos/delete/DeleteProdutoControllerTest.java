package com.fiap.msadminapi.application.produtos.delete;

import com.fiap.msadminapi.application.produtos.categoria.get.GetProdutoController;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.gateway.produto.BuscaProdutoInterface;
import com.fiap.msadminapi.domain.gateway.produto.DeletarProdutoInterface;
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
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class DeleteProdutoControllerTest {

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    DeletaProdutoUseCase useCase;

    DeletarProdutoInterface deletarProdutoInterface;

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

//    @Test
//    void deveRetornarOPresenterCorreto() {
//        var uuid = UUID.randomUUID();
//        var produtoModel = new ProdutoModel(UUID.fromString("f0989e76-ab12-4a75-ba9c-be0e3643daf"), "Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//
//        var produtoArray = new HashMap<>();
//        produtoArray.put("categoria", CategoriaEnum.LANCHE);
//        produtoArray.put("descricao", "Descricao 1");
//        produtoArray.put("nome", "Produto 1");
//        produtoArray.put("quantidade", 100);
//        produtoArray.put("uuid", UUID.fromString("f0989e76-ab12-4a75-ba9c-0be0e3643daf"));
//        produtoArray.put("valor", Float.parseFloat("10"));
//
//        when(produtoRepository.findByUuid(uuid)).thenReturn(produtoModel);
//
//        var presenter = controller.deletaProduto(uuid);
//
//        assertThat(presenter.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//    }

//    @Test
//    void deveGerarExcecao_QuandoRetornarOPresenterCorreto_StatusCodeDiferenteDeDuzentosEQuatro() {
//        var uuid = UUID.randomUUID();
//        when(produtoRepository.findByUuid(uuid))
//                .thenThrow(HttpServerErrorException.InternalServerError.class);
//
//        var presenter = controller.deletaProduto(uuid);
//        assertThat(presenter.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
