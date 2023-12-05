package br.com.atlantic.api.domain.model;

import br.com.atlantic.api.core.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "carrinho")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Carrinho extends BaseModel {
    @Column
    @NotBlank(message = "O campo 'Nome' não pode ser vazio.")
    private String nome;

    @Column
    @NotBlank(message = "O campo 'cpf' não pode ser vazio.")
    private String cpf;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CarrinhoItem> itens;

    @Column(nullable = false)
    private Integer qtdItens;

    @Column(nullable = false)
    private BigDecimal peso;

    @Column(nullable = false)
    private BigDecimal subTotal;

    @Column(nullable = false)
    private BigDecimal desconto;

    @Column(nullable = false)
    private BigDecimal frete;

    @Column(nullable = false)
    private BigDecimal total;
}
