package com.fiap.msadminapi.domain.useCase.produto;

import com.fiap.msadminapi.domain.entity.produto.Imagem;
import com.fiap.msadminapi.domain.entity.produto.Produto;
import com.fiap.msadminapi.domain.exception.produto.NomeNaoPodeSerVazioException;
import com.fiap.msadminapi.domain.exception.produto.ValorDoProdutoMenorQueZeroException;
import com.fiap.msadminapi.domain.gateway.producers.NovoProdutoProducerInterface;
import com.fiap.msadminapi.domain.gateway.produto.CriarProdutoInterface;
import com.fiap.msadminapi.domain.generic.output.OutputError;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.generic.output.OutputStatus;
import com.fiap.msadminapi.domain.generic.output.ProdutoOutput;
import com.fiap.msadminapi.domain.input.produto.CriarProdutoInput;
import com.fiap.msadminapi.domain.output.produto.CriaProdutoOutput;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class CriaProdutoUseCase {

    private final CriarProdutoInterface criaProdutoRepository;
    private final NovoProdutoProducerInterface novoProdutoProducerInterface;
    private OutputInterface criaProdutoOutput;

    @Transactional
    public void execute(CriarProdutoInput criarProdutoInput) {
        try {
            List<Imagem> imagemModels = criarProdutoInput.imagens().stream()
                    .map(imagem -> new Imagem(imagem.id(), imagem.nome(), imagem.url()))
                    .collect(Collectors.toList());

            Produto produto = new Produto(
                    criarProdutoInput.nome(),
                    criarProdutoInput.valor(),
                    criarProdutoInput.descricao(),
                    criarProdutoInput.categoria(),
                    criarProdutoInput.quantidade(),
                    imagemModels
            )
                    .criaProduto();

            this.criaProdutoRepository.criaProduto(produto);
            this.criaProdutoOutput = new CriaProdutoOutput(
                    produto,
                    new OutputStatus(201, "Created", "Produto criado")
            );

        } catch (NomeNaoPodeSerVazioException | ValorDoProdutoMenorQueZeroException nomeNaoPodeSerVazioException) {
            this.criaProdutoOutput = new OutputError(
                    nomeNaoPodeSerVazioException.getMessage(),
                    new OutputStatus(422, "Unprocessable Entity", nomeNaoPodeSerVazioException.getMessage())
            );
        } finally {
        if (this.criaProdutoOutput instanceof CriaProdutoOutput criaProdutoOutput) {
            ProdutoOutput produtoOutput = new ProdutoOutput(
                    criaProdutoOutput.getProduto().getUuid(),
                    criaProdutoOutput.getProduto().getNome(),
                    criaProdutoOutput.getProduto().getValor(),
                    criaProdutoOutput.getProduto().getDescricao(),
                    criaProdutoOutput.getProduto().getCategoria(),
                    criaProdutoOutput.getProduto().getQuantidade(),
                    criaProdutoOutput.getProduto().getImagens()
            );
            this.novoProdutoProducerInterface.send(produtoOutput);
        }
    }
    }
}
