package com.fiap.msadminapi.domain.gateway.producers;

import com.fiap.msadminapi.domain.generic.output.ProdutoOutput;

public interface NovoProdutoProducerInterface {
    void send(ProdutoOutput produtoOutput);
}
