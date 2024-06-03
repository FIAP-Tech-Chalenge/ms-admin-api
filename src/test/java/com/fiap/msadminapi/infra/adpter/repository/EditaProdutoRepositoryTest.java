package com.fiap.msadminapi.infra.adpter.repository;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.exception.produto.ProdutoNaoEncontradoException;
import com.fiap.msadminapi.domain.useCase.produto.EditaProdutoUseCase;
import com.fiap.msadminapi.infra.adpter.repository.produto.EditaProdutoRepository;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.when;

public class EditaProdutoRepositoryTest {

    @Mock
    private ProdutoRepository produtoRepository;

    private EditaProdutoRepository editaProdutoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        editaProdutoRepository = new EditaProdutoRepository(produtoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirEditarProduto() {
        var uuid = UUID.randomUUID();
        var produto = new Produto("Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 2);
        var produtoModel = new ProdutoModel(UUID.randomUUID(), "Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);

        when(produtoRepository.findByUuid(uuid)).thenReturn(produtoModel);

        try {
            editaProdutoRepository.editaProduto(produto, uuid);
        } catch (ProdutoNaoEncontradoException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Produto with UUID " + uuid + " not found.");
        }
    }

    @Test
    void deveGerarExececao_QuandoPermitirEditarProduto_ProdutoNaoEncontrado() {
        var uuid = UUID.randomUUID();
        var produto = new Produto("Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 2);

        when(produtoRepository.findByUuid(uuid)).thenReturn(null);

        try {
            editaProdutoRepository.editaProduto(produto, uuid);
        } catch (ProdutoNaoEncontradoException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Produto with UUID " + uuid + " not found.");
        }
    }

}
