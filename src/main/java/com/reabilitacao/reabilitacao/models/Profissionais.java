package com.reabilitacao.reabilitacao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profissionaissaude", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "numero_cedula")
})
@Getter
@Setter
public class Profissionais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_profissional;

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

    @NotBlank(message = "A localidade do profissional é obrigatória")
    @Column(nullable = false)
    private String localidade;

    @NotBlank(message = "O contacto é obrigatório")
    @Size(min = 9, max = 15, message = "O contacto deve ter entre 9 e 15 caracteres")
    @Column(nullable = false)
    private String contacto;

    @NotBlank(message = "A especialidade é obrigatória")
    @Column(nullable = false)
    private String especialidade;

    @NotBlank(message = "O número da cédula é obrigatório")
    @Size(min = 4, max = 10, message = "O número da cédula deve ter entre 4 e 10 dígitos")
    @Pattern(regexp = "\\d{4,10}", message = "A cédula deve conter apenas números")
    @Column(nullable = false, unique = true)
    private String numeroCedula;

    @Column(nullable = true)
    private boolean emailVerificado;

    @Column(nullable = true)
    private String tokenValidacao;

    // Getters personalizados
    public Integer getId() {
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

    @Override
    public String toString() {
        return "Profissionais{" +
                "id=" + id_profissional +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", numeroCedula='" + numeroCedula + '\'' +
                '}';
    }
}
