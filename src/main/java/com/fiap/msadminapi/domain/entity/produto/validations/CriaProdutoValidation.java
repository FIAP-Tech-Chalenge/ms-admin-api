package com.fiap.msadminapi.domain.entity.produto.validations;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.exception.produto.NomeNaoPodeSerVazioException;
import com.fiap.msadminapi.domain.exception.produto.ValorDoProdutoMenorQueZeroException;


public class CriaProdutoValidation {
    public Produto validaEntidade(Produto produto) throws NomeNaoPodeSerVazioException, ValorDoProdutoMenorQueZeroException {
        if (produto.getNome().isEmpty()) {
            throw new NomeNaoPodeSerVazioException("Nome não pode ser vazio");
        }
        if (produto.getValor() < 0) {
            throw new ValorDoProdutoMenorQueZeroException("Valor do produto é menor que 0");
        }
        return produto;
    }
}
