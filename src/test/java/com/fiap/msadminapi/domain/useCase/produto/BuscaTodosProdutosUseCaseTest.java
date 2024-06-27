package com.fiap.msadminapi.domain.useCase.produto;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.gateway.produto.BuscaProdutoInterface;
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
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BuscaTodosProdutosUseCaseTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    BuscaProdutoInterface buscaProdutoInterface;

    private BuscaTodosProdutosUseCase useCase;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
//        useCase = new BuscaTodosProdutosUseCase(new BuscarProdutoRepository(produtoRepository));
        useCase = new BuscaTodosProdutosUseCase(buscaProdutoInterface);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveInstanciarORepositorioCorreto() {
        assertThat(useCase.getBuscaProdutoInterface()).isInstanceOf(BuscaProdutoInterface.class);
    }

    @Test
    void devePermitirBuscarTodosProdutos() {

        var produto1Uuid = UUID.randomUUID();
        var produto2Uuid = UUID.randomUUID();

        var produto1 = new Produto("Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100, List.of());
        var produto2 = new Produto("Produto 2", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100, List.of());
        produto1.setUuid(produto1Uuid);
        produto2.setUuid(produto2Uuid);

        var listaProdutos = Arrays.asList(
                produto1,
                produto2
        );

        when(buscaProdutoInterface.findAll())
                .thenReturn(listaProdutos);

        useCase.execute();

        var output = useCase.getBuscaProdutoOutput();
        assertThat(output.getBody())
                .isEqualTo(listaProdutos);
        assertThat(output).isInstanceOf(BuscaTodosProdutoOutput.class);
        assertThat(output.getOutputStatus().getCode())
                .isEqualTo(200);
        assertThat(output.getOutputStatus().getCodeName())
                .isEqualTo("OK");
        assertThat(output.getOutputStatus().getMessage())
                .isEqualTo("Lista de produtos");
    }

    @Test
    void deveGerarExcecao_QuandoBuscarTodosProdutos_ErroDeServidor() {
        when(buscaProdutoInterface.findAll())
                .thenThrow(HttpServerErrorException.InternalServerError.class);

        useCase.execute();

        var output = useCase.getBuscaProdutoOutput();
        assertThat(output.getOutputStatus().getCode())
                .isEqualTo(500);
        assertThat(output.getOutputStatus().getCodeName())
                .isEqualTo("Internal Server Error");
        assertThat(output.getOutputStatus().getMessage())
                .isEqualTo("Erro no servidor");
    }

}
