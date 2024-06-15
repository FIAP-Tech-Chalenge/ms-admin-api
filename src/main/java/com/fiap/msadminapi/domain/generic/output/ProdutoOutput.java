package com.fiap.msadminapi.domain.generic.output;

import com.fiap.msadminapi.domain.entity.produto.Imagem;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProdutoOutput implements OutputInterface{
    private final UUID uuid;
    private final String nome;
    private final Float valor;
    private final String descricao;
    @Enumerated(EnumType.STRING)
    private final CategoriaEnum categoria;
    private final Integer quantidade;
    private final List<Imagem> imagens;

    public ProdutoOutput(UUID uuid, String nome, Float valor, String descricao, CategoriaEnum categoria, Integer quantidade, List<Imagem> imagens) {
        this.uuid = uuid;
        this.nome = nome;
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.imagens = imagens;
    }


    @Override
    public Object getBody() {
        return this;
    }

    @Override
    public OutputStatus getOutputStatus() {
        return new OutputStatus(200, "Ok", "Cliente encontrado");
    }
}
