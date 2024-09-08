package com.fiap.msadminapi.application.produtos.update.requests;

import com.fiap.msadminapi.domain.entity.produto.Imagem;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;

@Schema(description = "Request para atualizar um produto")
public record UpdateProdutoRequest(
        @Schema(description = "Nome do produto", example = "Lanche Exemplo") String nome,
        @Schema(description = "Valor do produto", example = "15.99") Float valor,
        @Schema(description = "Descrição do produto", example = "Descrição do Lanche Exemplo") String descricao,
        @Schema(description = "Categoria do produto", example = "LANCHE", allowableValues = {"LANCHE", "BEBIDA", "SOBREMESA"}) CategoriaEnum categoria,
        @Schema(description = "Quantidade do produto", example = "20") Integer quantidade,
        @Schema(description = "Data de criação do produto", example = "2024-08-26T14:17:31.244Z") Date dataCriacao,
        @Schema(description = "Lista de imagens do produto") List<Imagem> imagens) {

}