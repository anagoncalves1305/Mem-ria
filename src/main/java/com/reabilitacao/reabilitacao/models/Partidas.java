package com.reabilitacao.reabilitacao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "partidas")
@Setter
@Getter
public class Partidas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_partida;

    @NotNull(message = "O ID do utente é obrigatório")
    @Column(nullable = false)
    private Integer id_utente;

    @NotNull(message = "O ID do jogo é obrigatório")
    @Column(nullable = false)
    private Integer id_jogo;

    @NotNull(message = "O nível é obrigatório")
    @Column(nullable = false)
    private Integer nivel;

    @NotNull(message = "O tempo é obrigatório")
    @Column(nullable = false)
    private Integer tempo;

    @NotNull(message = "Indicação se usou ajuda é obrigatória")
    @Column(nullable = false)
    private Boolean usou_ajuda;

    @Column(name = "estrelas_ganhas", nullable = false)
    private Integer estrelas_ganhas;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime data_jogo;

    public Integer getId_partida() {
        return id_partida;
    }

    public Integer getId_utente() {
        return id_utente;
    }

    public Integer getNivel() {
        return nivel;
    }

    public Integer getTempo() {
        return tempo;
    }

    public Boolean getUsou_ajuda() {
        return usou_ajuda;
    }

    public LocalDateTime getData_jogo() {
        return data_jogo;
    }

    public Integer getEstrelas_ganhas() {
        return estrelas_ganhas;
    }

    public void setEstrelas_ganhas(Integer estrelas_ganhas) {
        this.estrelas_ganhas = estrelas_ganhas;
    }
}
