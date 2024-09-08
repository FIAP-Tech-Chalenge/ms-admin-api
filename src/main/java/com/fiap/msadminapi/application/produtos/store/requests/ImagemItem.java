package com.fiap.msadminapi.application.produtos.store.requests;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Item de imagem do produto")
public record ImagemItem(
        @Schema(description = "ID da imagem", example = "1") Long id,
        @Schema(description = "Nome da imagem", example = "Imagem Exemplo") String nome,
        @Schema(description = "URL da imagem", example = "http://exemplo.com/imagem.jpg") String url
) {
}