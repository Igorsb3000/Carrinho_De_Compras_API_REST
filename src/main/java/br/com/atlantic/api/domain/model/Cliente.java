package br.com.atlantic.api.domain.model;

import br.com.atlantic.api.core.base.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@Getter
@Setter
public class Cliente extends BaseModel {

    @Column
    @NotBlank(message = "O campo 'Nome' não pode ser vazio.")
    private String nome;

    @Column
    @Size(max = 20, message = "O campo 'cpf' é obrigatório.")
    private String cpf;
}
