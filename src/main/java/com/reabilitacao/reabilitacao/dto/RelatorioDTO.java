package com.reabilitacao.reabilitacao.dto;

import java.time.LocalDateTime;

public class RelatorioDTO {
    private LocalDateTime data;
    private String jogo;
    private Integer nivel;
    private Integer tempo;
    private Boolean usouAjuda;

    public RelatorioDTO(LocalDateTime data, String nome, Integer nivel, Integer tempo, Boolean usouAjuda) {
        this.data = data;
        this.jogo = nome;
        this.nivel = nivel;
        this.tempo = tempo;
        this.usouAjuda = usouAjuda;
    }

    public LocalDateTime getData() { return data; }
    public String getJogo() { return jogo; }
    public Integer getNivel() { return nivel; }
    public Integer getTempo() { return tempo; }
    public Boolean getUsouAjuda() { return usouAjuda; }
}
