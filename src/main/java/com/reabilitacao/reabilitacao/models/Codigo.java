package com.reabilitacao.reabilitacao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "codigo")
@Getter
@Setter
public class Codigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_codigo;

    @NotBlank(message = "O código é obrigatório")
    @Size(min = 4, max = 4, message = "O código deve ter exatamente 4 caracteres")
    @Column(nullable = false, unique = true, length = 4)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

    @Column(nullable = false, updatable = false)
    private LocalDateTime data_criacao;

    @PrePersist
    protected void onCreate() {
        this.data_criacao = LocalDateTime.now();
    }

    public Integer getId_codigo() {
        return id_codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public Utente getUtente() {
        return utente;
    }

    public LocalDateTime getData_criacao() {
        return data_criacao;
    }
}
