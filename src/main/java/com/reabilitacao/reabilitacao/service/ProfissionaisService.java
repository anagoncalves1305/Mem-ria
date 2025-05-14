package com.reabilitacao.reabilitacao.service;

import com.reabilitacao.reabilitacao.models.Profissionais;
import com.reabilitacao.reabilitacao.repository.ProfissionaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfissionaisService {

    @Autowired
    private ProfissionaisRepository profissionaisRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Profissionais salvarProfissionais(Profissionais profissional) {
        if (!validarPasswordForte(profissional.getPassword())) {
            throw new IllegalArgumentException("Password fraca. Deve ter pelo menos 8 caracteres, uma maiúscula, uma minúscula, um número e um símbolo.");
        }

        String passwordCodificada = passwordEncoder.encode(profissional.getPassword());
        profissional.setPassword(passwordCodificada);
        return profissionaisRepository.save(profissional);
    }

    private boolean validarPasswordForte(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$";
        return password != null && password.matches(regex);
    }

    public List<Profissionais> listarProfissionais() {
        List<Profissionais> profissionais = profissionaisRepository.findAll();
        System.out.println("Fetched Relatorios: " + profissionais);
        return profissionais;
    }

    public Optional<Profissionais> buscarPorId(Long id) {
        return profissionaisRepository.findById(id);
    }
}
