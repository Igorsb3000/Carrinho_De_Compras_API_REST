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

// Regras de Negócio

/*

	O Carrinho de Compras é um módulo do Atlantic, um sistema web para compras online de itens diversos. O
Carrinho de Compras apresenta como principal funcionalidade o checkout, que é a ação de registrar o usuário
responsável pela compra e os itens que ele deseja comprar, além do cálculo do valor a ser pago, que inclui o valor
dos itens e do frete a ser pago. Ao realizar um checkout, as seguintes regras devem ser seguidas.

- O valor do frete é  calculado com base no peso total de todos itens comprados: até 2 kg não é cobrado frete; acima de 2 kg e abaixo
de 10 kg é cobrado R$ 2,00 por kg; acima de 10 kg e abaixo de 50 kg é cobrado R$ 4,00 por kg; e acima de 50 kg é
cobrado R$ 7,00 por kg.
- Caso o carrinho de compras tenha mais de 5 itens, independente do seu peso ou valor, haverá um acréscimo de R$ 10,00 no frete.
- Carrinhos de compras que possuem mais de dois itens do mesmo tipo recebem 5% de desconto apenas no valor do frete.
- Por fim, carrinhos de compras que custam mais de R$ 500,00 recebem um desconto de 10% e mais de R$ 1000,00 recebem 20% de desconto. Vale ressaltar que este desconto
é aplicado somente ao valor dos itens, excluindo o valor do frete.
 
 */

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
        

        if (carrinho.getItens().size() == 0) //carrinho.getItens() == null || 
            throw new RuntimeException("Nenhum item no carrinho");

        // Retotalizar Item
        carrinho.getItens().forEach(v -> v.setValorTotal(v.getPreco()));

        // Totalizar
        carrinho.setQtdItens(carrinho.getItens().stream().mapToInt(v -> 1).reduce(0, Integer::sum));
        carrinho.setPeso(carrinho.getItens().stream().map(CarrinhoItem::getPeso).reduce(BigDecimal.ZERO, BigDecimal::add));
        carrinho.setSubTotal(carrinho.getItens().stream().map(CarrinhoItem::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add));

        // Valor do Frete
        BigDecimal valorFrete = calcularFrete(carrinho);

        // Mais de 5 itens no carrinho
        if (carrinho.getQtdItens() > 5)
            valorFrete = valorFrete.add(ADICIONAL_FRETE);

        // Desconto para mais de 2 itens do mesmo tipo
        valorFrete = calcularDescontoFrete(carrinho, valorFrete);

        // Desconto pelo valor da venda
        BigDecimal desconto = calcularDescontoCompra(carrinho);

        carrinho.setFrete(valorFrete.setScale(2, RoundingMode.HALF_UP));
        carrinho.setSubTotal(carrinho.getSubTotal().subtract(desconto).setScale(2, RoundingMode.HALF_UP));

        // Atualizar Totais finais
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
        
    	if (carrinho.getPeso().compareTo(BigDecimal.valueOf(50)) > 0)
            taxaFreteKg = new BigDecimal(7);
        else if (carrinho.getPeso().compareTo(BigDecimal.valueOf(10)) > 0)
            taxaFreteKg = new BigDecimal(4);
        else if (carrinho.getPeso().compareTo(BigDecimal.valueOf(2)) > 0)
            taxaFreteKg = new BigDecimal(2);
        else 
        	taxaFreteKg = BigDecimal.ZERO;
    	
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
    	
        if (carrinho.getSubTotal().compareTo(BigDecimal.valueOf(1000)) > 0)
            taxaDesconto = new BigDecimal("0.2");
        else if (carrinho.getSubTotal().compareTo(BigDecimal.valueOf(500)) > 0)
            taxaDesconto = new BigDecimal("0.1");

        return carrinho.getSubTotal().multiply(taxaDesconto);
    }
 
}
