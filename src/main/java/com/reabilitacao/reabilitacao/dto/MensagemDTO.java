package com.reabilitacao.reabilitacao.dto;

public class MensagemDTO {
    private int id_utente;
    private int id_profissional;
    private String texto;
    private String remetente;

    // Getters e Setters

    public int getId_utente() { return id_utente; }
    public void setId_utente(int id_utente) { this.id_utente = id_utente; }

    public int getId_profissional() { return id_profissional; }
    public void setId_profissional(int id_profissional) { this.id_profissional = id_profissional; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public String getRemetente() { return remetente; }
    public void setRemetente(String remetente) { this.remetente = remetente; }
}
