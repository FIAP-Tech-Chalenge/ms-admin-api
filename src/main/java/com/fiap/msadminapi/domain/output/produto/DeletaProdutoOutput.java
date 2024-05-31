package com.fiap.msadminapi.domain.output.produto;
import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class DeletaProdutoOutput implements OutputInterface {

    private final OutputStatus outputStatus;
    private final Produto produto;

    public DeletaProdutoOutput(Produto produtoEntity, OutputStatus outputStatus) {
        this.produto = produtoEntity;
        this.outputStatus = outputStatus;
    }

    @Override
    public Object getBody() {
        return this.produto;
    }
}

