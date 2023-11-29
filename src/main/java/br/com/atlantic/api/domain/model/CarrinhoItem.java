package br.com.atlantic.api.domain.model;

import br.com.atlantic.api.core.base.BaseModel;
import br.com.atlantic.api.domain.EnumAtlantic;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "carrinho_itens")
@Getter
@Setter
public class CarrinhoItem extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrinho_id")
    @JsonBackReference
    private Carrinho carrinho;

    @NotNull(message = "Produto não informado.")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo não informado.")
    private EnumAtlantic.TipoItem tipo;

    @NotNull(message = "Peso não informado.")
    private BigDecimal peso;

    @NotNull(message = "Preço não informado.")
    private BigDecimal preco;

    @NotNull(message = "Quantidade não informada.")
    private BigDecimal quantidade;

    @NotNull(message = "Valor Total não informado.")
    private BigDecimal valorTotal;
}
