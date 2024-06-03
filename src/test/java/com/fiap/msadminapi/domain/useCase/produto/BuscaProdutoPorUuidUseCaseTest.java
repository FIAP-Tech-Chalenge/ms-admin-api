package com.fiap.msadminapi.domain.useCase.produto;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.infra.adpter.repository.produto.BuscarProdutoRepository;
import com.fiap.msadminapi.infra.model.ProdutoModel;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.HttpServerErrorException;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class BuscaProdutoPorUuidUseCaseTest {

    @Mock
    private ProdutoRepository produtoRepository;

    private BuscaProdutoPorUuidUseCase useCase;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        useCase = new BuscaProdutoPorUuidUseCase(new BuscarProdutoRepository(produtoRepository));
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveInstanciarORepositorioCorreto() {
        var uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174009");
        useCase.execute(uuid);
        assertThat(useCase.getBuscaProduto()).isInstanceOf(BuscarProdutoRepository.class);
    }

    @Test
    void devePermitirBuscarProdutoPorUuid() {
        var uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174009");
        var produto = new ProdutoModel(uuid, "Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
        when(produtoRepository.findByUuid(uuid))
                .thenReturn(produto);

        useCase.execute(uuid);
        var output = useCase.getBuscaProdutoOutput();
        assertThat(output.getOutputStatus().getCode())
                .isEqualTo(200);
        assertThat(output.getOutputStatus().getCodeName())
                .isEqualTo("OK");
        assertThat(output.getOutputStatus().getMessage())
                .isEqualTo("Produto encontrado com sucesso");
    }

    @Test
    void deveGerarExcecao_devePermitirBuscarProdutoPorUuid_ProdutoNaoEncontrado() {
        var uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174009");
        when(produtoRepository.findByUuid(uuid))
                .thenReturn(null);

        useCase.execute(uuid);
        var output = useCase.getBuscaProdutoOutput();
        assertThat(output.getOutputStatus().getCode())
                .isEqualTo(404);
        assertThat(output.getOutputStatus().getCodeName())
                .isEqualTo("Not Found");
        assertThat(output.getOutputStatus().getMessage())
                .isEqualTo("Produto não encontrado");
    }

    @Test
    void deveGerarExcecao_devePermitirBuscarProdutoPorUuid_ErroDeServidor() {
        var uuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174009");
        when(produtoRepository.findByUuid(uuid))
                .thenThrow(HttpServerErrorException.InternalServerError.class);

        useCase.execute(uuid);

        var output = useCase.getBuscaProdutoOutput();
        assertThat(output.getOutputStatus().getCode())
                .isEqualTo(500);
        assertThat(output.getOutputStatus().getCodeName())
                .isEqualTo("Internal Server Error");
        assertThat(output.getOutputStatus().getMessage())
                .isEqualTo("Erro no servidor");
    }
}
