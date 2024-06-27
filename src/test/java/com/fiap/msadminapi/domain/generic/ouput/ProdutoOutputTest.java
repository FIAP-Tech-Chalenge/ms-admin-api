package com.fiap.msadminapi.domain.generic.ouput;

import com.fiap.msadminapi.domain.entity.produto.Imagem;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import com.fiap.msadminapi.domain.generic.output.ProdutoOutput;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ProdutoOutputTest {

    ProdutoOutput produtoOutput;

    @Test
    void deveRetornarOStatusCorreto() {
        var uuid = UUID.randomUUID();
        var nome = "Produto 1";
        var valor = Float.parseFloat("17.9");
        var descricao = "";
        var categoria = CategoriaEnum.LANCHE;
        var quantidade = 1;
        var imagem = new Imagem(Long.parseLong("123"), "Imagem 1", "https://imagem1.com.br");
        var imagens = List.of(imagem);
        produtoOutput = new ProdutoOutput(uuid, nome, valor, descricao, categoria, quantidade, imagens);

        assertThat(produtoOutput.getOutputStatus()).isInstanceOf(OutputStatus.class);
        assertThat(produtoOutput.getOutputStatus().getMessage()).isEqualTo("Cliente encontrado");
    }



}
