package com.fiap.msadminapi.domain.useCase.produto;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.exception.produto.ProdutoNaoEncontradoException;
import com.fiap.msadminapi.domain.gateway.produto.BuscaProdutoInterface;
import com.fiap.msadminapi.domain.gateway.produto.DeletarProdutoInterface;
import com.fiap.msadminapi.infra.adpter.repository.produto.BuscarProdutoRepository;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class DeletaProdutoUseCaseTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private DeletarProdutoInterface deletarProduto;

    @Mock
    private BuscaProdutoInterface produtoInterface;

    AutoCloseable openMocks;

    private DeletaProdutoUseCase useCase;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new DeletaProdutoUseCase(deletarProduto, produtoInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveInstanciarOsRepositoriosCorretos() {
        useCase.execute(UUID.randomUUID());
        assertThat(useCase.getDeletaProduto()).isInstanceOf(DeletarProdutoInterface.class);
        assertThat(useCase.getProdutoInterface()).isInstanceOf(BuscaProdutoInterface.class);
    }


//    @Test
//    void deveGerarExcecao_QuandoDeletarProduto_ProdutoNaoEncontrado() {
//        var uuid = UUID.fromString("74d8c418-4001-7004-a6a8-8265b4a2c496");
//        var produto = new Produto("Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//
//        try {
//            when(produtoInterface.encontraProdutoPorUuid(uuid))
//                    .thenThrow(ProdutoNaoEncontradoException.class);
//            doNothing().when(deletarProduto).deletaProduto(uuid);
//
//            useCase.execute(uuid);
//
//            var output = useCase.getDeletaProdutoOutput();
//            assertThat(output.getOutputStatus().getCode())
//                    .isEqualTo(404);
//            assertThat(output.getOutputStatus().getCodeName())
//                    .isEqualTo("Not Found");
//            assertThat(output.getOutputStatus().getMessage())
//                    .isEqualTo("Produto não encontrado");
//        } catch (ProdutoNaoEncontradoException e) {
//            assertThat(e.getMessage()).isEqualTo("Produto não encontrado");
//        }
//    }

    @Test
    void deveGerarExcecao_QuandoDeletarProduto_ErroServidor() {
        var uuid = UUID.randomUUID();
        when(produtoRepository.findByUuid(uuid))
                .thenThrow(HttpServerErrorException.InternalServerError.class);

        useCase.execute(uuid);

        var output = useCase.getDeletaProdutoOutput();
        assertThat(output.getOutputStatus().getCode())
                .isEqualTo(500);
        assertThat(output.getOutputStatus().getCodeName())
                .isEqualTo("Internal Server Error");
        assertThat(output.getOutputStatus().getMessage())
                .isEqualTo("Erro no servidor");
    }
}
