package com.fiap.msadminapi.application.produtos.categoria.get;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.gateway.produto.BuscaProdutoInterface;
import com.fiap.msadminapi.domain.useCase.produto.BuscaProdutoPorCategoriaUseCase;
import com.fiap.msadminapi.domain.useCase.produto.BuscaProdutoPorUuidUseCase;
import com.fiap.msadminapi.infra.adpter.repository.produto.BuscarProdutoRepository;
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
import static org.mockito.Mockito.when;

public class GetProdutoControllerTest {

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    BuscaProdutoPorUuidUseCase useCase;

    BuscaProdutoInterface buscaProdutoInterface;

    GetProdutoController controller;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new BuscaProdutoPorUuidUseCase(buscaProdutoInterface);
        controller = new GetProdutoController(produtoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornarOPresenterCorreto() {
        var uuid = UUID.fromString("f0989e76-ab12-4a75-ba9c-be0e3643daf");
        var produto = new Produto("Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100, List.of());
        produto.setUuid(uuid);

        var produtoArray = new HashMap<>();
        produtoArray.put("categoria", CategoriaEnum.LANCHE);
        produtoArray.put("descricao", "Descricao 1");
        produtoArray.put("nome", "Produto 1");
        produtoArray.put("quantidade", 100);
        produtoArray.put("uuid", UUID.fromString("f0989e76-ab12-4a75-ba9c-0be0e3643daf"));
        produtoArray.put("valor", Float.parseFloat("10"));
        produtoArray.put("imagens", List.of());

        try {
            when(buscaProdutoInterface.encontraProdutoPorUuid(uuid)).thenReturn(produto);

            var presenter = controller.getProduto(uuid);

            assertThat(presenter.getBody()).isEqualTo(produtoArray);
        } catch (Exception e) {}
    }

    @Test
    void deveGerarExcecao_QuandoRetornarOPresenterCorreto_StatusCodeDiferenteDeDuzentos() {
        var uuid = UUID.randomUUID();
        when(produtoRepository.findByUuid(uuid))
                .thenThrow(HttpClientErrorException.NotFound.class);

        var presenter = controller.getProduto(uuid);
        assertThat(presenter.getStatusCode().toString()).isEqualTo("404 NOT_FOUND");
    }

}
