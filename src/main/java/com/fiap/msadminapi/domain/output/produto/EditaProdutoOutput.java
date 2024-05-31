package com.fiap.msadminapi.domain.output.produto;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import lombok.Getter;

@Getter
public class EditaProdutoOutput implements OutputInterface {

    private final Produto produto;
    private final OutputStatus outputStatus;

    public EditaProdutoOutput(Produto produtoEntity, OutputStatus outputStatus) {
        this.produto = produtoEntity;
        this.outputStatus = outputStatus;
    }

    @Override
    public Object getBody() {
        return produto;
    }
}
