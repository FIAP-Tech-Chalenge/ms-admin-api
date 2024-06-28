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
        var valor = Float.parseFloat("17.9");
        var produto = new Produto(uuid, quantidade, categoria);
        produto.setValor(valor);
        produto.setCategoria(CategoriaEnum.LANCHE);

        assertThat(produto.getUuid()).isEqualTo(uuid);
        assertThat(produto.getQuantidade()).isEqualTo(quantidade);
        assertThat(produto.getCategoria()).isEqualTo(CategoriaEnum.LANCHE);
        assertThat(produto.getValor()).isEqualTo(valor);
        assertThat(produto.toString()).isEqualTo("Produto(uuid=" + uuid + ", quantidade=" + quantidade + ", valor=" + valor + ", categoria=" + CategoriaEnum.LANCHE + ")");
        assertThat(produto.getUuid().equals(uuid)).isTrue();
        assertThat(produto.getCategoria().equals(CategoriaEnum.LANCHE)).isTrue();
        assertThat(produto.getQuantidade().equals(quantidade)).isTrue();
        assertThat(produto.hashCode()).isNotZero();
    }

}
