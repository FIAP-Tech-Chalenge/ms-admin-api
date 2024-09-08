package com.fiap.msadminapi.application.produtos.store.requests;

import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Schema(description = "Request para criar um novo produto")
public record StoreProdutoRequest(
        @Schema(description = "UUID do produto", example = "123e4567-e89b-12d3-a456-426614174000") UUID uuid,
        @Schema(description = "Nome do produto", example = "Produto Exemplo") String nome,
        @Schema(description = "Valor do produto", example = "19.99") Float valor,
        @Schema(description = "Descrição do produto", example = "Descrição do Produto Exemplo") String descricao,
        @Schema(description = "Categoria do produto", example = "LANCHE", allowableValues = {"LANCHE", "BEBIDA", "SOBREMESA"}) CategoriaEnum categoria,
        @Schema(description = "Quantidade do produto", example = "10") Integer quantidade,
        @Schema(description = "Data de criação do produto", example = "2024-08-26T14:17:31.244Z") Date dataCriacao,
        @Schema(description = "Lista de imagens do produto") List<ImagemItem> imagens
) {
}