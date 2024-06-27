package com.fiap.msadminapi.infra.adpter.repository.produto;

import com.fiap.msadminapi.domain.entity.produto.Imagem;
import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.exception.produto.ProdutoNaoEncontradoException;
import com.fiap.msadminapi.infra.model.ImagemModel;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        var nome = "Produto 1";
        var valor = Float.parseFloat("10");
        var descricao = "";
        var categoria = CategoriaEnum.LANCHE;
        var quantidade = 100;
        var imagemModel = new ImagemModel();
        imagemModel.setId(Long.parseLong("123"));
        imagemModel.setNome("Imagem 1");
        imagemModel.setUrl("https://imagem1.com.br");
        var imagem = new Imagem(Long.parseLong("123"), "Imagem 1", "https://imagem1.com.br");
        var produtoModel = new ProdutoModel(nome, valor, descricao, categoria, quantidade, List.of(imagemModel));

        when(produtoRepository.findByUuidWithImages(uuid)).thenReturn(Optional.of(produtoModel));

        try {
            var produtoEncontrado = buscarProdutoRepository.encontraProdutoPorUuid(uuid);

            assertThat(produtoEncontrado).satisfies(p -> {
                assertThat(p.getNome()).isEqualTo(nome);
                assertThat(p.getValor()).isEqualTo(valor);
                assertThat(p.getDescricao()).isEqualTo(descricao);
                assertThat(p.getCategoria()).isEqualTo(categoria);
                assertThat(p.getQuantidade()).isEqualTo(quantidade);
                assertThat(p.getImagens()).isEqualTo(List.of(imagem));
            });
        } catch(ProdutoNaoEncontradoException e) {
            assertThat(e.getMessage()).isEqualTo("Produto anão encontrado");
        }
    }

    @Test
    void deveGerarExcecao_QuandoEncontrarProdutoPorUuid_ProdutoNaoEncontrado() {
        var uuid = UUID.randomUUID();
        when(produtoRepository.findByUuid(uuid)).thenReturn(null);

        try {
            buscarProdutoRepository.encontraProdutoPorUuid(uuid);
        } catch(ProdutoNaoEncontradoException e) {
            assertThat(e.getMessage()).isEqualTo("Produto não encontrado");
        }
    }

    @Test
    void devePermitirEncontrarProdutoPorCategoria() {
        var nome = "Produto 1";
        var valor = Float.parseFloat("10");
        var descricao = "";
        var categoria = CategoriaEnum.LANCHE;
        var quantidade = 100;
        var imagemModel = new ImagemModel();
        imagemModel.setId(Long.parseLong("123"));
        imagemModel.setNome("Imagem 1");
        imagemModel.setUrl("https://imagem1.com.br");
        var produtoModel = new ProdutoModel(nome, valor, descricao, categoria, quantidade, List.of(imagemModel));
        var produtoModelList = Arrays.asList(produtoModel);

        when(produtoRepository.findByCategoria(categoria)).thenReturn(produtoModelList);

        try {
            var produtosEncontrados = buscarProdutoRepository.encontraProdutoPorCategoria(categoria);

            assertThat(produtosEncontrados).satisfies(p -> {
                var prod = p.getFirst();
                assertThat(prod.getNome()).isEqualTo(nome);
                assertThat(prod.getValor()).isEqualTo(valor);
                assertThat(prod.getDescricao()).isEqualTo(descricao);
                assertThat(prod.getCategoria()).isEqualTo(categoria);
                assertThat(prod.getQuantidade()).isEqualTo(quantidade);
            });
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
