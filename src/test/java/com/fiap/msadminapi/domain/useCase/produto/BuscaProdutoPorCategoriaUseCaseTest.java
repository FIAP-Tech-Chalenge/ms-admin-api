package com.fiap.msadminapi.domain.useCase.produto;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.output.produto.BuscaTodosProdutoOutput;
import com.fiap.msadminapi.infra.adpter.repository.produto.BuscarProdutoRepository;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BuscaProdutoPorCategoriaUseCaseTest {

    @Mock
    private ProdutoRepository produtoRepository;

    private BuscaProdutoPorCategoriaUseCase useCase;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new BuscaProdutoPorCategoriaUseCase(new BuscarProdutoRepository(produtoRepository));
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveInstanciarORepositorioCorreto() {
        var categoria = "LANCHE";
        var uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174009");
        useCase.execute(categoria);
        assertThat(useCase.getProdutoRepository()).isInstanceOf(BuscarProdutoRepository.class);
    }

//    @Test
//    void devePermitirBuscarProdutoPorCategoria() {
//
//        var categoria = "LANCHE";
//        var produto1Uuid = UUID.randomUUID();
//        var produto2Uuid = UUID.randomUUID();
//
//        var produtoModel1 = new ProdutoModel(produto1Uuid, "Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//        var produtoModel2 = new ProdutoModel(produto2Uuid, "Produto 2", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//        var listaModelProdutos = Arrays.asList(
//                produtoModel1,
//                produtoModel2
//        );
//
//        var produto1 = new Produto("Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//        var produto2 = new Produto("Produto 2", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//        produto1.setUuid(produto1Uuid);
//        produto2.setUuid(produto2Uuid);
//
//        var listaProdutos = Arrays.asList(
//                produto1,
//                produto2
//        );
//
//        when(produtoRepository.findByCategoria(any(CategoriaEnum.class)))
//                .thenReturn(listaModelProdutos);
//
//        useCase.execute(categoria);
//
//        var output = useCase.getBuscaProdutoOutput();
//        assertThat(output.getBody())
//                .isEqualTo(listaProdutos);
//        assertThat(output).isInstanceOf(BuscaTodosProdutoOutput.class);
//        assertThat(output.getOutputStatus().getCode())
//                .isEqualTo(200);
//        assertThat(output.getOutputStatus().getCodeName())
//                .isEqualTo("OK");
//        assertThat(output.getOutputStatus().getMessage())
//                .isEqualTo("Lista de produtos");
//    }

    @Test
    void deveGerarExcecao_QuandoBuscarProdutoPorCategoria_ListaVazia() {

        var categoria = "BEBIDA";

        when(produtoRepository.findByCategoria(any(CategoriaEnum.class)))
                .thenReturn(Arrays.asList());

        useCase.execute(categoria);

        assertThat(useCase.getBuscaProdutoOutput().getOutputStatus().getMessage())
                .isEqualTo("Produto não encontrado");
        assertThat(useCase.getBuscaProdutoOutput().getOutputStatus().getCode())
                .isEqualTo(404);
    }

    @Test
    void deveGerarExcecao_QuandoBuscarProdutoPorCategoria_CategoriaInvalida() {
        var categoria = "TESTE";

        when(produtoRepository.findByCategoria(any(CategoriaEnum.class)))
                .thenReturn(Arrays.asList());

        useCase.execute(categoria);

        assertThat(useCase.getBuscaProdutoOutput().getOutputStatus().getCode())
                .isEqualTo(400);
        assertThat(useCase.getBuscaProdutoOutput().getOutputStatus().getMessage())
                .isEqualTo("Categoria inválida: " + categoria);
    }

    @Test
    void deveGerarExcecao_QuandoBuscarProdutoPorCategoria_ErroDeServidor() {
        var categoria = "LANCHE";

        when(produtoRepository.findByCategoria(any(CategoriaEnum.class)))
                .thenThrow(HttpServerErrorException.InternalServerError.class);

        useCase.execute(categoria);

        var output = useCase.getBuscaProdutoOutput();
        assertThat(output.getOutputStatus().getCode())
                .isEqualTo(500);
        assertThat(output.getOutputStatus().getCodeName())
                .isEqualTo("Internal Server Error");
        assertThat(output.getOutputStatus().getMessage())
                .isEqualTo("Erro no servidor");
    }

}
