package com.fiap.msadminapi.infra.repository;

import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProdutoRepositoryTest {

    @Mock
    private ProdutoRepository produtoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

//    @Test
//    void devePermitirSalvarProduto() {
//        var produto = new ProdutoModel(
//                UUID.randomUUID(),
//                "X-Burguer",
//                Float.parseFloat("17.9"),
//                "",
//                CategoriaEnum.LANCHE,
//                1
//        );
//        when(produtoRepository.save(produto))
//                .thenReturn(produto);
//
//        var produtoSalvo = produtoRepository.save(produto);
//
//        assertThat(produtoSalvo)
//                .isNotNull()
//                .isEqualTo(produto);
//        verify(produtoRepository, times(1)).save(produto);
//    }
//
//    @Test
//    void devePermitirEncontrarProdutoPorUuid() {
//        var uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174005");
//        var produto = new ProdutoModel(uuid, "Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//
//        when(produtoRepository.findByUuid(any(UUID.class)))
//                .thenReturn(produto);
//
//        var produtoEncontrado = produtoRepository.findByUuid(uuid);
//
//        assertThat(produtoEncontrado)
//                .isNotNull();
//        verify(produtoRepository, times(1)).findByUuid(uuid);
//    }
//
//    @Test
//    void devePermitirRemoverProdutoPorUuid() {
//        var uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174005");
//        var produto = new ProdutoModel(uuid, "Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//
//        produtoRepository.delete(produto);
//
//        verify(produtoRepository, times(1)).delete(any(ProdutoModel.class));
//    }
//
//    @Test
//    void devePermirListarTodosProdutos() {
//        var produto1 = new ProdutoModel(UUID.randomUUID(), "Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//        var produto2 = new ProdutoModel(UUID.randomUUID(), "Produto 2", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//        var listaProdutos = Arrays.asList(
//                produto1,
//                produto2
//        );
//        when(produtoRepository.findAll()).thenReturn(listaProdutos);
//
//        var produtosRecebidos = produtoRepository.findAll();
//
//        assertThat(produtosRecebidos)
//                .hasSize(2)
//                .containsExactly(produto1, produto2);
//        verify(produtoRepository, times(1)).findAll();
//    }
}
