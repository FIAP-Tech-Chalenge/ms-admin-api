package com.fiap.msadminapi.infra.adpter.repository;

import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.infra.adpter.repository.produto.DeletaProdutoRepository;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.when;

public class DeletaProdutoRepositoryTest {

    @Mock
    private ProdutoRepository produtoRepository;

    private DeletaProdutoRepository deletaProdutoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        deletaProdutoRepository = new DeletaProdutoRepository(produtoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirDeletarProduto() {
        var uuid = UUID.randomUUID();
        var produtoModel = new ProdutoModel(UUID.randomUUID(), "Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);

        when(produtoRepository.findByUuid(uuid)).thenReturn(produtoModel);

        deletaProdutoRepository.deletaProduto(uuid);
    }
}
