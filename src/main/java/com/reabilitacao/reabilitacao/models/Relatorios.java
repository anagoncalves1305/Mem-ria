package com.reabilitacao.reabilitacao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "relatorios") 
@Setter
@Getter
public class Relatorios {  
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_relatorio;

    @NotNull(message = "O ID do profissional é obrigatório")
    @Column(nullable = false)
    private Integer id_profissional;
    
    @NotNull(message = "O ID do utente é obrigatório")
    @Column(nullable = false)
    private Integer id_utente;

    @NotNull(message = "O ID do partida é obrigatório")
    @Column(nullable = false)
    private Integer id_partida;


public Integer getId_relatorio() {
    return id_relatorio;
}

public Integer getId_Profissionais() {
    return id_profissional;
}

public Integer getId_utente() {
    return id_utente;
}

public Integer getId_partida() {
    return id_partida;
}
}