package com.reabilitacao.reabilitacao.dto;

public class ConsultaDTO {
    private int id_utente;
    private int id_profissional;
    private String data; 
    private String estado;

    // Getters e Setters

    public int getId_utente() { return id_utente; }
    public void setId_utente(int id_utente) { this.id_utente = id_utente; }

    public int getId_profissional() { return id_profissional; }
    public void setId_profissional(int id_profissional) { this.id_profissional = id_profissional; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
