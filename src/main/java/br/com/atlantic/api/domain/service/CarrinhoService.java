package br.com.atlantic.api.domain.service;

import br.com.atlantic.api.core.base.BaseService;
import br.com.atlantic.api.domain.model.Carrinho;
import br.com.atlantic.api.domain.model.CarrinhoItem;
import br.com.atlantic.api.domain.repository.CarrinhoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;


@Service
public class CarrinhoService { //extends BaseService<Carrinho, CarrinhoRepository>
	
	@Autowired
    private CarrinhoRepository repository;
	
    private final BigDecimal ADICIONAL_FRETE = new BigDecimal("10");
    private final BigDecimal DESCONTO_FRETE_PERC = new BigDecimal("0.05");

    public Carrinho insert(Carrinho carrinho) {
        processarCarrinho(carrinho);
        return this.repository.save(carrinho);
    }
    
    public void processarCarrinho(Carrinho carrinho){
        carrinho.setQtdItens(0);
        carrinho.setPeso(BigDecimal.ZERO);
        carrinho.setSubTotal(BigDecimal.ZERO);
        carrinho.setDesconto(BigDecimal.ZERO);
        carrinho.setFrete(BigDecimal.ZERO);
        carrinho.setTotal(BigDecimal.ZERO);
        

        if (carrinho.getItens().size() == 0) {
        	throw new RuntimeException("Nenhum item no carrinho");
        }
            
        // Retotalizar Item
        carrinho.getItens().forEach(v -> v.setValorTotal(v.getPreco().multiply(v.getQuantidade())));

        // Totalizar
        carrinho.setQtdItens(carrinho.getItens().stream().mapToInt(v -> 1).reduce(0, Integer::sum));
        carrinho.setPeso(carrinho.getItens().stream().map(CarrinhoItem::getPeso).reduce(BigDecimal.ZERO, BigDecimal::add));
        carrinho.setSubTotal(carrinho.getItens().stream().map(CarrinhoItem::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add));

        // Valor do Frete
        BigDecimal valorFrete = calcularFrete(carrinho);

        // Mais de 5 itens no carrinho
        if (carrinho.getQtdItens() > 5) {
        	valorFrete = valorFrete.add(ADICIONAL_FRETE);
        }    

        // Desconto para mais de 2 itens do mesmo tipo
        valorFrete = calcularDescontoFrete(carrinho, valorFrete);

        // Desconto pelo valor da venda
        BigDecimal desconto = calcularDescontoCompra(carrinho);

        carrinho.setFrete(valorFrete.setScale(2, RoundingMode.HALF_UP));
        carrinho.setSubTotal(carrinho.getSubTotal().subtract(desconto).setScale(2, RoundingMode.HALF_UP));

        // Atualizar Total
        carrinho.setTotal(
                carrinho.getSubTotal()
                .subtract(carrinho.getDesconto())
                .add(carrinho.getFrete())
                .setScale(2, RoundingMode.HALF_UP)
        );
    }
    
    
    public BigDecimal calcularFrete(Carrinho carrinho) {
    	BigDecimal taxaFreteKg;
    	BigDecimal valorFrete;
        
    	if (carrinho.getPeso().compareTo(BigDecimal.valueOf(50)) > 0) {
    		taxaFreteKg = new BigDecimal(7);
    	}
        else if (carrinho.getPeso().compareTo(BigDecimal.valueOf(10)) > 0) {
        	taxaFreteKg = new BigDecimal(4);
        }
        else if (carrinho.getPeso().compareTo(BigDecimal.valueOf(2)) > 0) {
        	taxaFreteKg = new BigDecimal(2);
        }
        else {
        	taxaFreteKg = BigDecimal.ZERO;
        }
    	valorFrete = taxaFreteKg.multiply(carrinho.getPeso());
    	return valorFrete;
    }
    
    public BigDecimal calcularDescontoFrete(Carrinho carrinho, BigDecimal antigoValorFrete) {
    	BigDecimal novoValorFrete = null;
    	var tipos = carrinho.getItens().stream().collect(Collectors.groupingBy(CarrinhoItem::getTipo, Collectors.counting()));
        
		if (tipos.entrySet().stream().anyMatch(v -> v.getValue() > 2)){
            BigDecimal descontoFrete = antigoValorFrete.multiply(DESCONTO_FRETE_PERC);
            novoValorFrete = antigoValorFrete.subtract(descontoFrete);
        } else {
        	novoValorFrete = antigoValorFrete;
        }
		
        return novoValorFrete;
    }
    
    public BigDecimal calcularDescontoCompra(Carrinho carrinho) {
    	BigDecimal taxaDesconto = BigDecimal.ZERO;
    	
        if (carrinho.getSubTotal().compareTo(BigDecimal.valueOf(1000)) > 0) {
        	taxaDesconto = new BigDecimal("0.2");
        }
        else if (carrinho.getSubTotal().compareTo(BigDecimal.valueOf(500)) > 0) {
        	taxaDesconto = new BigDecimal("0.1");
        }
        return carrinho.getSubTotal().multiply(taxaDesconto);
    }
 
}
