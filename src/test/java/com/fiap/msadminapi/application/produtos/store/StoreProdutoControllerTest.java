package com.fiap.msadminapi.application.produtos.store;

import com.fiap.msadminapi.application.produtos.delete.DeleteProdutoController;
import com.fiap.msadminapi.application.produtos.store.requests.ImagemItem;
import com.fiap.msadminapi.application.produtos.store.requests.StoreProdutoRequest;
import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.gateway.produto.CriarProdutoInterface;
import com.fiap.msadminapi.domain.useCase.produto.DeletaProdutoUseCase;
import com.fiap.msadminapi.infra.repository.ProdutoImagensRepository;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class StoreProdutoControllerTest {

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    ProdutoImagensRepository produtoImagensRepository;

    @Mock
    CriarProdutoInterface criarProdutoInterface;

    StoreProdutoRequest storeProdutoRequest;

    StoreProdutoController controller;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        controller = new StoreProdutoController(produtoRepository, produtoImagensRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornarOPresenterCorreto() throws Exception {
        var nome = "Produto 1";
        var valor = Float.parseFloat("17.9");
        var descricao = "";
        var categoria = CategoriaEnum.LANCHE;
        var quantidade = 1;
        var dataCriacao = new Date(2024, 12, 06);
        var imagem = new ImagemItem("Imagem 1", "https://imagem2.com.br");
        var imagens = List.of(imagem);
        var storeProdutoRequest = new StoreProdutoRequest(nome, valor, descricao, categoria, quantidade, dataCriacao, imagens);

        var presenter = controller.criaProduto(storeProdutoRequest);
        assertThat(presenter.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
