package com.fiap.msadminapi.domain.useCase.produto;

import com.fiap.msadminapi.domain.entity.produto.Imagem;
import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.exception.produto.ProdutoNaoEncontradoException;
import com.fiap.msadminapi.domain.gateway.produto.BuscaProdutoInterface;
import com.fiap.msadminapi.domain.gateway.produto.CriarProdutoInterface;
import com.fiap.msadminapi.domain.gateway.produto.EditaProdutoInterface;
import com.fiap.msadminapi.domain.input.produto.CriarProdutoInput;
import com.fiap.msadminapi.domain.input.produto.EditaProdutoInput;
import com.fiap.msadminapi.infra.adpter.repository.produto.CriaProtutoRepository;
import com.fiap.msadminapi.infra.adpter.repository.produto.EditaProdutoRepository;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoImagensRepository;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class EditaProdutoUseCaseTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private EditaProdutoInterface editaProdutoInterface;

    @Mock
    private BuscaProdutoInterface buscaProdutoInterface;

    @Mock
    private EditaProdutoRepository editaProdutoRepository;

    private EditaProdutoUseCase useCase;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        editaProdutoRepository = new EditaProdutoRepository(produtoRepository);
        useCase = new EditaProdutoUseCase(editaProdutoInterface, buscaProdutoInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveInstanciarOsRepositoriosCorretos() {
        assertThat(useCase.getEditaProduto()).isInstanceOf(EditaProdutoInterface.class);
        assertThat(useCase.getProdutoInterface()).isInstanceOf(BuscaProdutoInterface.class);
    }
//
//    @Test
//    void devePermitirEditarProduto() {
//        var uuid = UUID.randomUUID();
//        var produtoInput = new EditaProdutoInput("Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100, new Date(2024,6,1));
//        var produtoModel = new ProdutoModel(UUID.randomUUID(), "Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//        var produto = new Produto("Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//
//        try {
//            when(buscaProdutoInterface.encontraProdutoPorUuid(uuid))
//                    .thenReturn(produto);
//            when(produtoRepository.save(produtoModel))
//                    .thenReturn(produtoModel);
//            doNothing().when(editaProdutoInterface).editaProduto(produto, uuid);
//
//            useCase.execute(produtoInput, uuid);
//
//            var output = useCase.getEditaProdutoOutput();
//            assertThat(output.getOutputStatus().getCode())
//                    .isEqualTo(200);
//            assertThat(output.getOutputStatus().getCodeName())
//                    .isEqualTo("OK");
//            assertThat(output.getOutputStatus().getMessage())
//                    .isEqualTo("Produto editado com sucesso");
//            assertThat(output.getBody())
//                    .isInstanceOf(Produto.class);
//            assertThat(output.getBody())
//                    .isEqualTo(produto);
//        } catch (ProdutoNaoEncontradoException e) {
//            assertThat(e.getMessage()).isEqualTo("Produto não encontrado");
//        }
//    }

//    @Test
//    void deveGerarExcecao_QuandoEditarProduto_ProdutoNaoEncontrado() {
//        var uuid = UUID.randomUUID();
//        var produtoInput = new EditaProdutoInput("Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100, new Date(2024,6,1));
//
//        try {
//            when(buscaProdutoInterface.encontraProdutoPorUuid(uuid))
//                    .thenThrow(ProdutoNaoEncontradoException.class);
//
//            useCase.execute(produtoInput, uuid);
//
//        } catch (ProdutoNaoEncontradoException e) {
//            assertThat(e.getMessage()).isEqualTo("Produto não encontrado");
//        }
//    }

//    @Test
//    void deveGerarExcecao_QuandoEditarProduto_ErroServidor() {
//        var uuid = UUID.randomUUID();
//        var produtoInput = new EditaProdutoInput("Produto 1", Float.parseFloat("-10"), "Descricao 1", CategoriaEnum.LANCHE, 100, new Date(2024,6,1));
//        when(produtoRepository.findByUuid(uuid))
//                .thenThrow(HttpServerErrorException.InternalServerError.class);
//
//        useCase.execute(produtoInput, uuid);
//
//        var output = useCase.getEditaProdutoOutput();
//        assertThat(output.getOutputStatus().getCode())
//                .isEqualTo(500);
//        assertThat(output.getOutputStatus().getCodeName())
//                .isEqualTo("Internal Server Error");
//        assertThat(output.getOutputStatus().getMessage())
//                .isEqualTo("Erro no servidor");
//    }
}
