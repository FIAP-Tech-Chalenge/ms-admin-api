package com.fiap.msadminapi.domain.entity.pedido;

import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ProdutoTest {

    @Test
    void devePermitirInstanciarProdutoESuasPropriedades() {
        var uuid = UUID.randomUUID();
        var quantidade = 1;
        var categoria = CategoriaEnum.BEBIDA;
        var produto = new Produto(uuid, quantidade, categoria);

        assertThat(produto.getUuid()).isEqualTo(uuid);
        assertThat(produto.getQuantidade()).isEqualTo(quantidade);
        assertThat(produto.getCategoria()).isEqualTo(categoria);
    }

}
