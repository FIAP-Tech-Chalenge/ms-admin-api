package com.fiap.msadminapi.domain.entity.produto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Imagem(
        @JsonProperty("id") Long id,
        @JsonProperty("nome") String nome,
        @JsonProperty("url") String url) {
}
