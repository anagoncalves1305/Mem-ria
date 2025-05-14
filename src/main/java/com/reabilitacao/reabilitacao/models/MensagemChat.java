package com.reabilitacao.reabilitacao.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensagens_chat")
public class MensagemChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int idUtente;
    private int idProfissional;
    private String texto;
    private String remetente;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime data;
    
    @Column(nullable = false)
    private boolean lida = false;


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idUtente) { this.idUtente = idUtente; }

    public int getIdProfissional() { return idProfissional; }
    public void setIdProfissional(int idProfissional) { this.idProfissional = idProfissional; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public String getRemetente() { return remetente; }
    public void setRemetente(String remetente) { this.remetente = remetente; }

    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
	
    public boolean isLida() {
        return lida;
    }
    
    public void setLida(boolean lida) {
        this.lida = lida;
    }
    public MensagemChat(int idUtente, int idProfissional, String texto, String remetente) {
        this.idUtente = idUtente;
        this.idProfissional = idProfissional;
        this.texto = texto;
        this.remetente = remetente;
        this.data = LocalDateTime.now();
        this.lida = false;
    }
	public MensagemChat() {
	}
    
}
