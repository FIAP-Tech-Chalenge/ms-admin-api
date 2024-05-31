package com.fiap.msadminapi.domain.useCase.produto;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.gateway.produto.BuscaProdutoInterface;
import com.fiap.msadminapi.domain.generic.output.OutputError;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import com.fiap.msadminapi.domain.output.produto.BuscaTodosProdutoOutput;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class BuscaTodosProdutosUseCase {

    private final BuscaProdutoInterface buscaProdutoInterface;
    private OutputInterface buscaProdutoOutput;

    public void execute() {
        try {
            List<Produto> listProdutos = this.buscaProdutoInterface.findAll();

            buscaProdutoOutput = new BuscaTodosProdutoOutput(
                    listProdutos,
                    new OutputStatus(200, "OK", "Lista de produtos")
            );

        } catch (Exception e) {
            buscaProdutoOutput = new OutputError(
                    e.getMessage(),
                    new OutputStatus(500, "Internal Server Error", "Erro no servidor")
            );
        }
    }
}
