package com.fiap.msadminapi.domain.input.pedido;


import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;

import java.util.UUID;

public record ProdutoPedidoInput(UUID uuid, Integer quantidade, CategoriaEnum categoria) {
}
