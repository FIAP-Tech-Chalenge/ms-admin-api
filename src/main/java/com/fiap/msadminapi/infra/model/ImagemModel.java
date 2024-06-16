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
    private UUID uuid;

    @Column(name = "nome")
    public String nome;

    @Column(name = "url")
    public String url;

    @ManyToOne
    @JoinColumn(name = "produto_uuid")
    public ProdutoModel produto;

    public ImagemModel(UUID uuid, String nome, String url) {
        this.uuid = uuid;
        this.nome = nome;
        this.url = url;
    }

}
