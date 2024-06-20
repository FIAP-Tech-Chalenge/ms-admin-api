package com.fiap.msadminapi.infra.model;

import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name ="produtos")
public class ProdutoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String nome;
    private Float valor;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private CategoriaEnum categoria;
    private Integer quantidade;
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ImagemModel> imagens;

    public ProdutoModel(String nome, Float valor, String descricao, CategoriaEnum categoria, Integer quantidade, List<ImagemModel> imagens) {
        this.nome = nome;
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.imagens = imagens;
    }



}