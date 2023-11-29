package br.com.atlantic.api.domain.service;

import br.com.atlantic.api.core.base.BaseService;
import br.com.atlantic.api.domain.model.Carrinho;
import br.com.atlantic.api.domain.model.CarrinhoItem;
import br.com.atlantic.api.domain.repository.CarrinhoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CarrinhoService extends BaseService<Carrinho, CarrinhoRepository> {

    private void processarCarrinho(Carrinho carrinho){
        carrinho.setQtdItens(0);
        carrinho.setPeso(BigDecimal.ZERO);
        carrinho.setSubTotal(BigDecimal.ZERO);
        carrinho.setDesconto(BigDecimal.ZERO);
        carrinho.setFrete(BigDecimal.ZERO);
        carrinho.setTotal(BigDecimal.ZERO);

        if (carrinho.getItens() == null || carrinho.getItens().size() == 0)
            throw new RuntimeException("Nenhum Item no carrinho");

        // Retotalizar Item
        carrinho.getItens().forEach(v -> v.setValorTotal(v.getValorUnitario().multiply(v.getQuantidade())));

        // Totalizar
        carrinho.setQtdItens(carrinho.getItens().stream().mapToInt(v -> 1).reduce(0, Integer::sum));
        carrinho.setPeso(carrinho.getItens().stream().map(CarrinhoItem::getPeso).reduce(BigDecimal.ZERO, BigDecimal::add));
        carrinho.setSubTotal(carrinho.getItens().stream().map(CarrinhoItem::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add));

        //  Regras de negócio

        



        // Atualizar Totais finais
        carrinho.setTotal(carrinho.getSubTotal()
                .subtract(carrinho.getDesconto())
                .add(carrinho.getFrete())
        );
    }

    @Override
    public Carrinho insert(Carrinho model) {
        processarCarrinho(model);
        return super.insert(model);
    }
}
