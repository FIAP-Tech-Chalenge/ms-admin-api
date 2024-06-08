package com.fiap.msadminapi.domain.presenters.cliente.produto;

import com.fiap.msadminapi.domain.output.produto.DeletaProdutoOutput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteProdutoPresenterTest {

    @Mock
    DeletaProdutoOutput output;

    DeleteProdutoPresenter presenter;

    AutoCloseable openMocks;

    @BeforeEach
    public void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        presenter = new DeleteProdutoPresenter(output);
    }

//    @AfterEach
//    public void tearDown() throws Exception {
//        openMocks.close();
//    }

    @Test
    void devePermitirRetornarUmOutputCorreto() {
        var outputRetornado = presenter.getOutput();
        assertThat(outputRetornado).isInstanceOf(DeletaProdutoOutput.class);
    }

    @Test
    void devePermitirRetornarUmArrayCorreto() {
        var array = presenter.toArray();
        assertThat(array).isEmpty();
    }
}
