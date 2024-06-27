package com.fiap.msadminapi.application.produtos.store;


import com.fiap.msadminapi.application.produtos.store.requests.ImagemItem;
import com.fiap.msadminapi.application.produtos.store.requests.StoreProdutoRequest;
import com.fiap.msadminapi.application.response.GenericResponse;
import com.fiap.msadminapi.application.response.PresenterResponse;
import com.fiap.msadminapi.domain.entity.produto.Imagem;
import com.fiap.msadminapi.domain.generic.output.OutputInterface;
import com.fiap.msadminapi.domain.input.produto.CriarProdutoInput;
import com.fiap.msadminapi.domain.output.produto.CriaProdutoOutput;
import com.fiap.msadminapi.domain.presenters.cliente.produto.StoreProdutoPresenter;
import com.fiap.msadminapi.domain.useCase.produto.CriaProdutoUseCase;
import com.fiap.msadminapi.infra.adpter.repository.produto.CriaProtutoRepository;
import com.fiap.msadminapi.infra.queue.kafka.producers.NovoProdutoProducer;
import com.fiap.msadminapi.infra.repository.ProdutoImagensRepository;
import com.fiap.msadminapi.infra.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/produto")
public class StoreProdutoController {
    private final ProdutoRepository produtoRepository;
    private final ProdutoImagensRepository produtoImagensRepository;

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String servers;

    @PostMapping
    @Operation(tags = {"admin"})
    public ResponseEntity<Object> criaProduto(@RequestBody StoreProdutoRequest criarProdutoRequest) {
        List<Imagem> imagens = new ArrayList<>();

        for (ImagemItem imagemItem : criarProdutoRequest.imagens()) {
            imagens.add(new Imagem(imagemItem.id(), imagemItem.nome(), imagemItem.url()));
        }

        OutputInterface outputInterface = getOutputInterface(criarProdutoRequest, imagens);
        if (outputInterface.getOutputStatus().getCode() != 201) {
            return new GenericResponse().response(outputInterface);
        }

        StoreProdutoPresenter presenter = new StoreProdutoPresenter((CriaProdutoOutput) outputInterface);
        return new PresenterResponse().response(presenter);
    }

    private OutputInterface getOutputInterface(StoreProdutoRequest criarProdutoRequest, List<Imagem> imagens) {
        CriarProdutoInput criaProdutoInput = new CriarProdutoInput(
                criarProdutoRequest.nome(),
                criarProdutoRequest.valor(),
                criarProdutoRequest.descricao(),
                criarProdutoRequest.categoria(),
                criarProdutoRequest.quantidade(),
                criarProdutoRequest.dataCriacao(),
                imagens
        );
        CriaProdutoUseCase useCase = new CriaProdutoUseCase(
                new CriaProtutoRepository(produtoRepository,
                produtoImagensRepository),
                new NovoProdutoProducer(servers)
        );
        useCase.execute(criaProdutoInput);
        return useCase.getCriaProdutoOutput();
    }
}
