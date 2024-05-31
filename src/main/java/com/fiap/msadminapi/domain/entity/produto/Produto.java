package com.fiap.msadminapi.domain.entity.produto;

import com.fiap.msadminapi.domain.entity.produto.validations.CriaProdutoValidation;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.exception.produto.NomeNaoPodeSerVazioException;
import com.fiap.msadminapi.domain.exception.produto.ValorDoProdutoMenorQueZeroException;
import com.fiap.msadminapi.domain.input.produto.EditaProdutoInput;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Data
@Getter
@Setter
public class Produto {
    private UUID uuid;
    private String nome;
    private Float valor;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private CategoriaEnum categoria;
    private Integer quantidade;
    private List<Imagem> imagens;

    public Produto(String nome, Float valor, String descricao, CategoriaEnum categoria, Integer quantidade) {
        this.nome = nome;
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.quantidade = quantidade;
    }

    public Produto criaProduto() throws NomeNaoPodeSerVazioException, ValorDoProdutoMenorQueZeroException {
        return new CriaProdutoValidation().validaEntidade(this);
    }

    public void atualizaProduto(EditaProdutoInput editaProdutoInput) throws NomeNaoPodeSerVazioException, ValorDoProdutoMenorQueZeroException {
        this.setNome(editaProdutoInput.nome());
        this.setValor(editaProdutoInput.valor());
        this.setDescricao(editaProdutoInput.descricao());
        this.setCategoria(editaProdutoInput.categoria());
        this.setQuantidade(editaProdutoInput.quantidade());
        new CriaProdutoValidation().validaEntidade(this);
    }
}
