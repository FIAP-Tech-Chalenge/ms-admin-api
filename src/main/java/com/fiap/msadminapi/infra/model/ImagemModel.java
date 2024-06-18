package com.fiap.msadminapi.infra.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "imagens")
public class ImagemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid")
    private Long id;

    @Column(name = "nome")
    public String nome;

    @Column(name = "url")
    public String url;

    @ManyToOne
    @JoinColumn(name = "produto_uuid")
    public ProdutoModel produto;

    public ImagemModel(Long id, String nome, String url) {
        this.id = id;
        this.nome = nome;
        this.url = url;
    }
}
