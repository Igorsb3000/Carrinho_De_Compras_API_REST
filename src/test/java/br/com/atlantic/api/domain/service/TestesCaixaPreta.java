package br.com.atlantic.api.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import br.com.atlantic.api.domain.EnumAtlantic;
import br.com.atlantic.api.domain.model.Carrinho;
import br.com.atlantic.api.domain.model.CarrinhoItem;
import br.com.atlantic.api.domain.repository.CarrinhoRepository;

@ExtendWith(MockitoExtension.class)
public class TestesCaixaPreta {
	
	@InjectMocks
    private CarrinhoService service;
	
	@Mock
	private CarrinhoRepository mockRepository;

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
    public void templateCasosTeste01(BigDecimal peso, BigDecimal valorEsperadoFrete) {
        // ARRANGE
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
        
        when(mockRepository.save(any(Carrinho.class))).thenReturn(carrinho);
        
        // ACT
        // Chamando o método a ser testado
        Carrinho carrinhoSalvo = service.insert(carrinho);

        // ASSERT
        // Verificações
        assertEquals(valorEsperadoFrete, carrinhoSalvo.getFrete());
    }

    
    @ParameterizedTest
    @CsvSource({
            "0, 0.00",
            "1, 0.00",
            "4, 0.00",
            "5, 0.00",
            "6, 10.00"
    })
    public void templateCasosTeste02(int quantidadeItens, BigDecimal valorAcrescimoNoFrete) {
        // ARRANGE
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
            // Alternando os tipos dos itens
            EnumAtlantic.TipoItem tipoItem = EnumAtlantic.TipoItem.values()[i % EnumAtlantic.TipoItem.values().length];
            item.setTipo(tipoItem);
            carrinho.getItens().add(item);
        }
        
        if (quantidadeItens == 0) {
        	// ACT & ASSERT
        	RuntimeException exception = assertThrows(RuntimeException.class, () -> service.insert(carrinho));
            String mensagemEsperada = "Nenhum item no carrinho";
            
            assertEquals(mensagemEsperada, exception.getMessage());
        
        } else {
        	when(mockRepository.save(any(Carrinho.class))).thenReturn(carrinho);
        	// ACT
            // Chamando o método a ser testado
        	Carrinho carrinhoSalvo = service.insert(carrinho);

        	// ASSERT
            // Verificações
            assertEquals(valorAcrescimoNoFrete, carrinhoSalvo.getFrete());
        }
    }

    @ParameterizedTest
    @CsvSource({
    		"0, 5, 20.00",
            "1, 5, 20.00",
            "2, 5, 20.00",
            "3, 5, 19.00",
            "4, 5, 19.00",
            "5, 5, 19.00"
    })
    public void templateCasosTeste03(int quantidadeItensMesmoTipo, int quantidadeItens, BigDecimal valorEsperadoDesconto) {
        // ARRANGE
    	// Cenário de teste
        Carrinho carrinho = new Carrinho();
        carrinho.setNome("Nome do Cliente");
        carrinho.setCpf("123456789");
        carrinho.setItens(new ArrayList<>());

        // Adicionando itens ao carrinho com a quantidade fornecida pelo teste parametrizado
        for (int i = 0; i < quantidadeItensMesmoTipo; i++) {
            CarrinhoItem item = new CarrinhoItem();
            item.setPeso(new BigDecimal(2));// cada item possui 2kg pois 2kg * 5 = 10kg (R$20,00 de frete)
            item.setPreco(BigDecimal.ONE);
            item.setTipo(EnumAtlantic.TipoItem.CASA);
            carrinho.getItens().add(item);
        }
        
        EnumAtlantic.TipoItem[] tiposDisponiveis = EnumAtlantic.TipoItem.values();
        
        for (int i = quantidadeItensMesmoTipo; i < quantidadeItens; i++) {
            CarrinhoItem item = new CarrinhoItem();
            item.setPeso(new BigDecimal(2));// cada item possui 2kg pois 2kg * 5 = 10kg (R$20,00 de frete)
            item.setPreco(BigDecimal.ONE);
            EnumAtlantic.TipoItem tipoItem = tiposDisponiveis[i % tiposDisponiveis.length];
            item.setTipo(tipoItem);
            carrinho.getItens().add(item);
        }
        
        when(mockRepository.save(any(Carrinho.class))).thenReturn(carrinho);
        
        // ACT
        // Chamando o método a ser testado
        Carrinho carrinhoSalvo = service.insert(carrinho);

        // ASSERT
        // Verificações
        assertEquals(valorEsperadoDesconto, carrinhoSalvo.getFrete());
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
    public void templateCasosTeste04(BigDecimal valorDaCompra, BigDecimal valorEsperadoCompraComDesconto) {
    	// ARRANGE
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

        when(mockRepository.save(any(Carrinho.class))).thenReturn(carrinho);
        
        // ACT
        // Chamando o método a ser testado
        Carrinho carrinhoSalvo = service.insert(carrinho);

        // ASSERT
        // Verificações
        assertEquals(valorEsperadoCompraComDesconto, carrinhoSalvo.getSubTotal());
    }
    
}
