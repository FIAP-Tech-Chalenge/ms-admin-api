//package com.fiap.msadminapi.domain.useCase.produto;
//
//import com.fiap.msadminapi.domain.entity.produto.Imagem;
//import com.fiap.msadminapi.domain.entity.produto.Produto;
//import com.fiap.msadminapi.domain.enums.produto.CategoriaEnum;
//import com.fiap.msadminapi.domain.gateway.produto.CriarProdutoInterface;
//import com.fiap.msadminapi.domain.input.produto.CriarProdutoInput;
//import com.fiap.msadminapi.infra.adpter.repository.produto.CriaProtutoRepository;
//import com.fiap.msadminapi.infra.model.ProdutoModel;
//import com.fiap.msadminapi.infra.repository.ProdutoImagensRepository;
//import com.fiap.msadminapi.infra.repository.ProdutoRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Arrays;
//import java.util.Date;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//public class CriaProdutoUseCaseTest {
//
//    @Mock
//    private ProdutoRepository produtoRepository;
//
//    @Mock
//    private ProdutoImagensRepository produtoImagensRepository;
//
//    @Mock
//    private CriarProdutoInterface criarProdutoRepository;
//
//    @Mock
//    private CriaProtutoRepository criaProtutoRepository;
//
//    private CriaProdutoUseCase useCase;
//
//    AutoCloseable openMocks;
//
//    @BeforeEach
//    void setup(){
//        openMocks = MockitoAnnotations.openMocks(this);
//        criarProdutoRepository = new CriaProtutoRepository(produtoRepository, produtoImagensRepository);
//        useCase = new CriaProdutoUseCase(criarProdutoRepository);
//    }
//
//    @AfterEach
//    void tearDown() throws Exception {
//        openMocks.close();
//    }
//
//    @Test
//    void deveInstanciarORepositorioCorreto() {
//        assertThat(useCase.getCriaProdutoRepository()).isInstanceOf(CriaProtutoRepository.class);
//    }
//
//    @Test
//    void devePermitirCriarProduto() {
//        var imagem = new Imagem("titulo", "https://imagem.com");
//        var imagens = Arrays.asList(imagem, imagem);
//        var produtoInput = new CriarProdutoInput("Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100, new Date(2024,6,1), imagens);
//        var produtoModel = new ProdutoModel(UUID.randomUUID(), "Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//        var produto = new Produto("Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//
//        when(produtoRepository.save(produtoModel))
//                .thenReturn(produtoModel);
//        when(criaProtutoRepository.criaProduto(produto))
//                .thenReturn(produto);
//
//        useCase.execute(produtoInput);
//
//        var output = useCase.getCriaProdutoOutput();
//        assertThat(output.getOutputStatus().getCode())
//                .isEqualTo(201);
//        assertThat(output.getOutputStatus().getCodeName())
//                .isEqualTo("Created");
//        assertThat(output.getOutputStatus().getMessage())
//                .isEqualTo("Produto criado");
//        assertThat(output.getBody())
//                .isInstanceOf(Produto.class);
//    }
//
//    @Test
//    void deveGerarExcecao_QuandoCriarProduto_NomeVazio() {
//        var imagem = new Imagem("titulo", "https://imagem.com");
//        var imagens = Arrays.asList(imagem, imagem);
//        var produtoInput = new CriarProdutoInput("", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100, new Date(2024,6,1), imagens);
//        var produtoModel = new ProdutoModel(UUID.randomUUID(), "Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//        var produto = new Produto("Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//
//        when(produtoRepository.save(produtoModel))
//                .thenReturn(produtoModel);
//        when(criaProtutoRepository.criaProduto(produto))
//                .thenReturn(produto);
//
//        useCase.execute(produtoInput);
//
//        var output = useCase.getCriaProdutoOutput();
//        assertThat(output.getOutputStatus().getCode())
//                .isEqualTo(422);
//        assertThat(output.getOutputStatus().getCodeName())
//                .isEqualTo("Unprocessable Entity");
//        assertThat(output.getOutputStatus().getMessage())
//                .isEqualTo("Nome não pode ser vazio");
//    }
//
//    @Test
//    void deveGerarExcecao_QuandoCriarProduto_ValorProdutoMenorQueZero() {
//        var imagem = new Imagem("titulo", "https://imagem.com");
//        var imagens = Arrays.asList(imagem, imagem);
//        var produtoInput = new CriarProdutoInput("Produto 1", Float.parseFloat("-10"), "Descricao 1", CategoriaEnum.LANCHE, 100, new Date(2024,6,1), imagens);
//        var produtoModel = new ProdutoModel(UUID.randomUUID(), "Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//        var produto = new Produto("Produto 1", Float.parseFloat("10"), "Descricao 1", CategoriaEnum.LANCHE, 100);
//
//        when(produtoRepository.save(produtoModel))
//                .thenReturn(produtoModel);
//        when(criaProtutoRepository.criaProduto(produto))
//                .thenReturn(produto);
//
//        useCase.execute(produtoInput);
//
//        var output = useCase.getCriaProdutoOutput();
//        assertThat(output.getOutputStatus().getCode())
//                .isEqualTo(422);
//        assertThat(output.getOutputStatus().getCodeName())
//                .isEqualTo("Unprocessable Entity");
//        assertThat(output.getOutputStatus().getMessage())
//                .isEqualTo("Valor do produto é menor que 0");
//    }
//}
