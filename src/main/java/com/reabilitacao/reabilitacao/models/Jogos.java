package com.reabilitacao.reabilitacao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "jogos", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "numero_utente")
})
@Getter
@Setter
public class Jogos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_jogo;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false)
    private String nome_jogo;

    
    public Integer getId_jogo() {
        return id_jogo;
    }

    public String getNome_jogo() {
        return nome_jogo;
    }

}