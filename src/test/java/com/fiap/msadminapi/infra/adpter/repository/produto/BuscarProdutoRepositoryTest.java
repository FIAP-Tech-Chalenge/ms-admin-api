package com.fiap.msadminapi.infra.adpter.repository.produto;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.exception.produto.ProdutoNaoEncontradoException;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BuscarProdutoRepositoryTest {

    @Mock
    private ProdutoRepository produtoRepository;

    private BuscarProdutoRepository buscarProdutoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        buscarProdutoRepository = new BuscarProdutoRepository(produtoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirEncontrarProdutoPorUuid() {
        var uuid = UUID.randomUUID();
        var produtoModel = new ProdutoModel(uuid, "Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
        var produto = new Produto("Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
        produto.setUuid(uuid);
        when(produtoRepository.findByUuid(uuid)).thenReturn(produtoModel);

        try {
            var produtoEncontrado = buscarProdutoRepository.encontraProdutoPorUuid(uuid);

            assertThat(produtoEncontrado).isEqualTo(produto);
        } catch(ProdutoNaoEncontradoException e) {
            assertThat(e.getMessage()).isEqualTo("Produto não encontrado");
        }
    }

    @Test
    void deveGerarExcecao_QuandoEncontrarProdutoPorUuid_ProdutoNaoEncontrado() {
        var uuid = UUID.randomUUID();
        when(produtoRepository.findByUuid(uuid)).thenReturn(null);

        try {
            buscarProdutoRepository.encontraProdutoPorUuid(uuid);
        } catch(ProdutoNaoEncontradoException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Produto não encontrado");
        }
    }

    @Test
    void devePermitirEncontrarProdutoPorCategoria() {
        var categoria = CategoriaEnum.LANCHE;
        var uuid = UUID.randomUUID();
        var produtoModel = new ProdutoModel(uuid, "Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
        var produto = new Produto("Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
        produto.setUuid(uuid);
        var produtoModelList = Arrays.asList(produtoModel);
        var produtoList = Arrays.asList(produto);
        when(produtoRepository.findByCategoria(categoria)).thenReturn(produtoModelList);

        try {
            var produtosEncontrados = buscarProdutoRepository.encontraProdutoPorCategoria(categoria);

            assertThat(produtosEncontrados).isEqualTo(produtoList);
        } catch(ProdutoNaoEncontradoException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Produto não encontrado");
        }
    }

    @Test
    void deveGerarExcecao_QuandoEncontrarProdutoPorCategoria_ProdutoNaoEncontrado() {
        var categoria = CategoriaEnum.LANCHE;

        when(produtoRepository.findByCategoria(categoria)).thenReturn(Arrays.asList());

        try {
            buscarProdutoRepository.encontraProdutoPorCategoria(categoria);
        } catch(ProdutoNaoEncontradoException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Produto não encontrado");
        }
    }
}
