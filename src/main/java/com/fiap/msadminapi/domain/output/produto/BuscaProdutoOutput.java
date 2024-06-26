package com.fiap.msadminapi.domain.output.produto;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class BuscaProdutoOutput implements OutputInterface {
    private Produto produto;
    private OutputStatus outputStatus;

    public BuscaProdutoOutput(Produto produto, OutputStatus outputStatus) {
        this.produto = produto;
        this.outputStatus = outputStatus;
    }

    @Override
    public Object getBody() {
        return this.produto;
    }
}