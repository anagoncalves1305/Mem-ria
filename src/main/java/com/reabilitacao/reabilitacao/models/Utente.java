package com.reabilitacao.reabilitacao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "utentes", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "numero_utente")
})
@Getter
@Setter
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_utente;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "A data de nascimento é obrigatória")
    @Column(nullable = false)
    private String data_nascimento;

    @NotBlank(message = "O número do utente é obrigatório")
    @Column(nullable = false, unique = true)
    private String numero_utente;

    @NotBlank(message = "O contacto é obrigatório")
    @Size(min = 9, max = 15, message = "O contacto deve ter entre 9 e 15 caracteres")
    @Column(nullable = false)
    private String contacto;

    @Column(nullable = true)
    private Integer id_profissional;

    @Column(nullable = true)
    private String status;

    @Column(nullable = true)
    private boolean emailVerificado;

    @Column(nullable = true)
    private String tokenValidacao;

    public Integer getId() {
        return id_utente;
    }

    public Integer getId_Profissional() {
        return id_profissional;
    }

    public String getTokenValidacao() {
        return tokenValidacao;
    }

    public void setTokenValidacao(String tokenValidacao) {
        this.tokenValidacao = tokenValidacao;
    }

    public boolean isEmailVerificado() {
        return emailVerificado;
    }

    public void setEmailVerificado(boolean emailVerificado) {
        this.emailVerificado = emailVerificado;
    }

    public void setProfissional(Integer id_profissional) {
        this.id_profissional = id_profissional;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id_utente +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void setEstrelas(Integer novasEstrelas) {		
	}
}

