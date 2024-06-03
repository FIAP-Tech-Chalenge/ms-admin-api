package com.fiap.msadminapi.domain.useCase.produto;


import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
import com.fiap.msadminapi.domain.exception.produto.ProdutoNaoEncontradoException;
import com.fiap.msadminapi.domain.gateway.produto.BuscaProdutoInterface;
import com.fiap.msadminapi.domain.gateway.produto.DeletarProdutoInterface;
import com.fiap.msadminapi.domain.generic.output.OutputError;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import com.fiap.msadminapi.domain.output.produto.DeletaProdutoOutput;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class DeletaProdutoUseCase {

    private final DeletarProdutoInterface deletaProduto;
    private final BuscaProdutoInterface produtoInterface;
    private OutputInterface deletaProdutoOutput;

    public void execute(UUID uuid) {
        try {
            Produto produtoEntity = this.produtoInterface.encontraProdutoPorUuid(uuid);

            this.deletaProduto.deletaProduto(produtoEntity.getUuid());
            this.deletaProdutoOutput = new DeletaProdutoOutput(
                    produtoEntity,
                    new OutputStatus(204, "No content", "Produto deletado com sucesso")
            );
        } catch (ProdutoNaoEncontradoException e) {
            this.deletaProdutoOutput = new OutputError(
                    e.getMessage(),
                    new OutputStatus(404, "Not Found", "Produto não encontrado")
            );
        } catch (Exception e) {
            this.deletaProdutoOutput = new OutputError(
                    e.getMessage(),
                    new OutputStatus(500, "Internal Server Error", "Erro no servidor")
            );
        }
    }
}



