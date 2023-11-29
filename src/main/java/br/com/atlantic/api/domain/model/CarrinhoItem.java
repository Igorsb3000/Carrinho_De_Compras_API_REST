package br.com.atlantic.api.domain.model;

import br.com.atlantic.api.core.base.BaseModel;
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

    @NotNull(message = "Código informado.")
    private Integer codigo;

    @NotNull(message = "Produto não informado.")
    private String produto;

    @NotNull(message = "Valor Unitário não informado.")
    private BigDecimal valorUnitario;

    @NotNull(message = "Quantidade não informada.")
    private BigDecimal quantidade;

    @NotNull(message = "Valor Total não informado.")
    private BigDecimal valorTotal;

    @NotNull(message = "Peso não informado.")
    private BigDecimal peso;
}
