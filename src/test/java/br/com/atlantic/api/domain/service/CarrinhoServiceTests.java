package br.com.atlantic.api.domain.service;

import br.com.atlantic.api.domain.EnumAtlantic;
import br.com.atlantic.api.domain.model.Carrinho;
import br.com.atlantic.api.domain.model.CarrinhoItem;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ActiveProfiles("dev")
@SpringBootTest
public class CarrinhoServiceTests {

    @Autowired
    private CarrinhoService service;

    @ParameterizedTest
    @CsvSource({
            "0.00, 0.00",
            "0.10, 0.00",
            "1.90, 0.00",
            "2.00, 0.00",
            "2.10, 4.20",
            "9.90, 19.80",
            "10.00, 20.00",
            "10.10, 40.40",
            "49.90, 199.60",
            "50.00, 200.00",
            "50.10, 350.70",
            "100.00, 700.00"
    })
    public void testProcessarCarrinhoVerificandoFrenteComDeterminadoPeso(BigDecimal peso, BigDecimal valorEsperadoFrete) {
        // Cenário de teste
        Carrinho carrinho = new Carrinho();
        carrinho.setNome("Nome do Cliente");
        carrinho.setCpf("123456789");
        carrinho.setItens(new ArrayList<>());

        // Adicionando um item ao carrinho com o peso fornecido pelo teste parametrizado
        CarrinhoItem item = new CarrinhoItem();
        item.setPeso(peso);

        // Fixando o valor unitário e a quantidade
        item.setPreco(BigDecimal.TEN);
        item.setQuantidade(BigDecimal.ONE);
        item.setValorTotal(BigDecimal.TEN);
        item.setTipo(EnumAtlantic.TipoItem.CASA);

        carrinho.getItens().add(item);

        // Chamando o método a ser testado
        service.processarCarrinho(carrinho);

        // Verificações
        assertEquals(valorEsperadoFrete, carrinho.getFrete());
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0.00",
            "1, 0.00",
            "4, 0.00",
            "5, 0.00",
            "6, 9.50"
    })
    public void testProcessarCarrinhoVerificandoFreteComAcrescimo(int quantidadeItens, BigDecimal valorEsperadoFrete) {
        // Cenário de teste
        Carrinho carrinho = new Carrinho();
        carrinho.setNome("Nome do Cliente");
        carrinho.setCpf("123456789");
        carrinho.setItens(new ArrayList<>());

        // Adicionando itens ao carrinho com a quantidade fornecida pelo teste parametrizado
        for (int i = 0; i < quantidadeItens; i++) {
            CarrinhoItem item = new CarrinhoItem();
            item.setPeso(BigDecimal.ZERO);
            item.setPreco(BigDecimal.ZERO);
            item.setQuantidade(BigDecimal.ONE);
            item.setTipo(EnumAtlantic.TipoItem.CASA);
            carrinho.getItens().add(item);
        }

        if (quantidadeItens == 0) {
            assertThrows(RuntimeException.class, () -> service.processarCarrinho(carrinho));
        } else {
            // Chamando o método a ser testado
            service.processarCarrinho(carrinho);

            // Verificações
            assertEquals(valorEsperadoFrete, carrinho.getFrete());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "0, 5, 20.00",
            "2, 5, 20.00",
            "3, 5, 19.00",
            "4, 5, 19.00",
            "5, 5, 19.00"
    })
    public void testProcessarCarrinhoVerificandoFreteComDesconto(int quantidadeItens, int quantidadeTotal, BigDecimal valorEsperadoDesconto) {
        // Cenário de teste
        Carrinho carrinho = new Carrinho();
        carrinho.setNome("Nome do Cliente");
        carrinho.setCpf("123456789");
        carrinho.setItens(new ArrayList<>());

        // Adicionando itens ao carrinho com a quantidade fornecida pelo teste parametrizado
        for (int i = 0; i < quantidadeItens; i++) {
            CarrinhoItem item = new CarrinhoItem();
            item.setPeso(new BigDecimal(2));
            item.setPreco(BigDecimal.ONE);
            item.setTipo(EnumAtlantic.TipoItem.CASA);
            carrinho.getItens().add(item);
        }
        EnumAtlantic.TipoItem[] tiposDisponiveis = EnumAtlantic.TipoItem.values();
        for (int i = quantidadeItens; i < quantidadeTotal; i++) {
            CarrinhoItem item = new CarrinhoItem();
            item.setPeso(new BigDecimal(2));
            item.setPreco(BigDecimal.ONE);

            EnumAtlantic.TipoItem tipoItem = tiposDisponiveis[i % tiposDisponiveis.length];
            item.setTipo(tipoItem);

            carrinho.getItens().add(item);
        }

        // Chamando o método a ser testado
        service.processarCarrinho(carrinho);

        // Verificações
        assertEquals(valorEsperadoDesconto, carrinho.getFrete());
    }

    @ParameterizedTest
    @CsvSource({
            "0.00, 0.00",
            "0.01, 0.01",
            "499.99, 499.99",
            "500.00, 500.00",
            "500.01, 450.01",
            "999.99, 899.99",
            "1000.00, 900.00",
            "1000.01, 800.01",
            "2500.00, 2000.00"
    })
    public void testProcessarCarrinhoVerificandoCompraComDesconto(BigDecimal valorDaCompra, BigDecimal valorEsperadoCompraComDesconto) {
        // Cenário de teste
        Carrinho carrinho = new Carrinho();
        carrinho.setNome("Nome do Cliente");
        carrinho.setCpf("123456789");
        carrinho.setItens(new ArrayList<>());

        // Adicionando itens ao carrinho
        CarrinhoItem item = new CarrinhoItem();
        item.setPeso(BigDecimal.ZERO);
        item.setPreco(valorDaCompra);
        item.setQuantidade(BigDecimal.ONE);
        item.setTipo(EnumAtlantic.TipoItem.CASA);
        carrinho.getItens().add(item);

        // Chamando o método a ser testado
        service.processarCarrinho(carrinho);

        // Verificações
        assertEquals(valorEsperadoCompraComDesconto, carrinho.getSubTotal());
    }
}
