package com.api.testetecnico.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "tb_endereco")
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @NotBlank
    @Size(max = 100)
    @Schema(example = "Rua Alfabeto")
    private String logradouro;

    @NotBlank
    @Size(max = 8)
    @Schema(example = "11122233")
    private String cep;

    @NotBlank
    @Size(max = 5)
    @Schema(example = "35")
    private String numero;

    @NotBlank
    @Size(max = 40)
    @Schema(example = "Cidade Grande")
    private String cidade;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(example = "PRINCIPAL")
    private TipoEndereco tipo;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    @Hidden
    private Pessoa pessoa;

    public Integer getId() {
        return id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public TipoEndereco getTipo() {
        return tipo;
    }

    public void setTipo(TipoEndereco tipo) {
        this.tipo = tipo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
