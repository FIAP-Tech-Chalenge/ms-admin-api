package com.fiap.msadminapi.domain.useCase.produto;

import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.exception.produto.ProdutoNaoEncontradoException;
import com.fiap.msadminapi.domain.gateway.produto.BuscaProdutoInterface;
import com.fiap.msadminapi.domain.gateway.produto.EditaProdutoInterface;
import com.fiap.msadminapi.domain.generic.output.OutputError;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import com.fiap.msadminapi.domain.input.produto.EditaProdutoInput;
import com.fiap.msadminapi.domain.output.produto.EditaProdutoOutput;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class EditaProdutoUseCase {

    private final EditaProdutoInterface editaProduto;
    private final BuscaProdutoInterface produtoInterface;
    private OutputInterface editaProdutoOutput;

    public void execute(EditaProdutoInput editaProdutoInput, UUID uuid) {
        try {
            Produto produtoEntity = this.produtoInterface.encontraProdutoPorUuid(uuid);
            produtoEntity.atualizaProduto(editaProdutoInput);

            this.editaProduto.editaProduto(produtoEntity, uuid);
            this.editaProdutoOutput = new EditaProdutoOutput(
                    produtoEntity,
                    new OutputStatus(200, "OK", "Produto editado com sucesso")
            );
        } catch (ProdutoNaoEncontradoException e) {
            this.editaProdutoOutput = new OutputError(
                    e.getMessage(),
                    new OutputStatus(404, "Not Found", "Produto não encontrado")
            );
        } catch (Exception e) {
            this.editaProdutoOutput = new OutputError(
                    e.getMessage(),
                    new OutputStatus(500, "Internal Server Error", "Erro no servidor")
            );
        }
    }
}
