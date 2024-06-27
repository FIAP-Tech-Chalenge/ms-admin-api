package com.fiap.msadminapi.domain.entity.produto.validations;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.exception.produto.NomeNaoPodeSerVazioException;
import com.fiap.msadminapi.domain.exception.produto.ValorDoProdutoMenorQueZeroException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CriaProdutoValidationTest {

    @Test
    void retornaExcecao_QuandoCriaProduto_ProdutoComNomeVazio() {
        var validation = new CriaProdutoValidation();
        var produto = new Produto("", Float.parseFloat("17.9"), "", CategoriaEnum.LANCHE, 1, List.of());
        assertThatThrownBy(() -> validation.validaEntidade(produto))
                .isInstanceOf(NomeNaoPodeSerVazioException.class);
    }

    @Test
    void retornaExcecao_QuandoCriaProduto_ProdutoComValorMenorQueZero() {
        var validation = new CriaProdutoValidation();
        var produto = new Produto("Produto 1", Float.parseFloat("-10.0"), "", CategoriaEnum.LANCHE, 1, List.of());
        assertThatThrownBy(() -> validation.validaEntidade(produto))
                .isInstanceOf(ValorDoProdutoMenorQueZeroException.class);
    }

}
