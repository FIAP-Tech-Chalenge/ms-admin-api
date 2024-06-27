package com.fiap.msadminapi.application.produtos.update;

import com.fiap.msadminapi.application.produtos.update.requests.UpdateProdutoRequest;
import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.gateway.produto.BuscaProdutoInterface;
import com.fiap.msadminapi.domain.gateway.produto.EditaProdutoInterface;
import com.fiap.msadminapi.infra.adpter.repository.produto.BuscarProdutoRepository;
import com.fiap.msadminapi.infra.adpter.repository.produto.EditaProdutoRepository;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UpdateProdutoControllerTest {

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    BuscarProdutoRepository buscarProdutoRepository;

    @Mock
    BuscaProdutoInterface buscaProdutoInterface;

    UpdateProdutoRequest updateProdutoRequest;

    UpdateProdutoController controller;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        controller = new UpdateProdutoController(produtoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveRetornarOPresenterCorreto() {
        var uuid = UUID.randomUUID();
        var nome = "Produto 1";
        var valor = Float.parseFloat("17.9");
        var descricao = "";
        var categoria = CategoriaEnum.LANCHE;
        var quantidade = 1;
        var dataCriacao = new Date(2024, 12, 06);
        updateProdutoRequest = new UpdateProdutoRequest(nome, valor, descricao, categoria, quantidade, dataCriacao, List.of());
        var produto = new Produto(nome, valor, descricao, categoria, quantidade, List.of());
        produto.setUuid(uuid);
        try {
            when(buscarProdutoRepository.encontraProdutoPorUuid(uuid))
                    .thenReturn(produto);
            doNothing().when(produtoRepository.save(new ProdutoModel()));

            var presenter = controller.editaProduto(uuid, updateProdutoRequest);
            assertThat(presenter.getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (Exception e) {}
    }

    @Test
    void deveGerarExcecao_QuandoAoRetornarOPresenterCorreto_StatusCodeDiferenteDe201() {
        var uuid = UUID.randomUUID();
        var nome = "Produto 1";
        var valor = Float.parseFloat("17.9");
        var descricao = "";
        var categoria = CategoriaEnum.LANCHE;
        var quantidade = 1;
        var dataCriacao = new Date(2024, 12, 06);
        updateProdutoRequest = new UpdateProdutoRequest(nome, valor, descricao, categoria, quantidade, dataCriacao, List.of());

        var presenter = controller.editaProduto(uuid, updateProdutoRequest);
        assertThat(presenter.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
