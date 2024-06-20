package com.fiap.msadminapi.infra.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "produto_imagens")
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

}
