package com.reabilitacao.reabilitacao.service;

import com.reabilitacao.reabilitacao.models.Utente;
import com.reabilitacao.reabilitacao.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Utente salvarUtente(Utente utente) {
        if (!validarPasswordForte(utente.getPassword())) {
            throw new IllegalArgumentException("Password fraca. Deve ter pelo menos 8 caracteres, uma maiúscula, uma minúscula, um número e um símbolo.");
        }

        String passwordCodificada = passwordEncoder.encode(utente.getPassword());
        utente.setPassword(passwordCodificada);
        return utenteRepository.save(utente);
    }

    private boolean validarPasswordForte(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$";
        return password != null && password.matches(regex);
    }

    public List<Utente> listarUtentes() {
        List<Utente> utentes = utenteRepository.findAll();
        System.out.println("Fetched Utentes: " + utentes);
        return utentes;
    }

    public Optional<Utente> buscarPorId(Long id) {
        return utenteRepository.findById(id);
    }

    public List<Utente> listarPorProfissional(Integer idProfissional) {
        return utenteRepository.findByIdProfissional(idProfissional);
    }

    public boolean associarProfissional(Long id, Integer idProfissional) {
        Optional<Utente> optionalUtente = utenteRepository.findById(id);
        if (optionalUtente.isPresent()) {
            Utente utente = optionalUtente.get();
            utente.setProfissional(idProfissional);
            utenteRepository.save(utente);
            return true;
        }
        return false;
    }

    public boolean atualizarEstrelas(Long id_utente, Integer novasEstrelas) {
        Optional<Utente> utenteOpt = utenteRepository.findById(id_utente);
        if (utenteOpt.isPresent()) {
            Utente utente = utenteOpt.get();
            utente.setEstrelas(novasEstrelas);
            utenteRepository.save(utente);
            return true;
        }
        return false;
    }
}
