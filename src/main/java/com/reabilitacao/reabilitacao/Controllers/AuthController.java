package com.reabilitacao.reabilitacao.Controllers;

import com.reabilitacao.reabilitacao.models.Profissionais;
import com.reabilitacao.reabilitacao.models.Utente;
import com.reabilitacao.reabilitacao.repository.ProfissionaisRepository;
import com.reabilitacao.reabilitacao.repository.UtenteRepository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;

@Controller
public class AuthController {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private ProfissionaisRepository profissionaisRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, String>> login(@RequestParam String email, @RequestParam String password) {
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        Utente utente = utenteRepository.findByEmail(email);
        if (utente != null && passwordEncoder.matches(password, utente.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("id", String.valueOf(utente.getId()));
            response.put("nome", utente.getNome());
            response.put("role", "utente");
            return ResponseEntity.ok(response);
        }

        Profissionais profissional = profissionaisRepository.findByEmail(email);
        if (profissional != null && passwordEncoder.matches(password, profissional.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("id", String.valueOf(profissional.getId()));
            response.put("nome", profissional.getNome());
            response.put("role", "profissional");
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Email ou senha inválidos."));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/verificar-dados")
    @ResponseBody
    public ResponseEntity<String> verificarDados(
            @RequestParam String email,
            @RequestParam String tipo,
            @RequestParam String numeroUtente) {

        if (tipo.equals("utente")) {
            Utente utente = utenteRepository.findByEmail(email);
            if (utente != null && utente.getNumero_utente().equals(numeroUtente)) {
                return ResponseEntity.ok("dados_validados");
            }
        } else if (tipo.equals("profissional")) {
            Profissionais profissional = profissionaisRepository.findByEmail(email);
            if (profissional != null && profissional.getNumeroCedula().equals(numeroUtente)) {
                return ResponseEntity.ok("dados_validados");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Dados incorretos. Verifica o email e o número fornecido.");
    }

    @PostMapping("/atualizar-password")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> atualizarPassword(
            @RequestParam String email,
            @RequestParam String novaPassword) {

        if (novaPassword == null || novaPassword.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Nova password inválida.");
        }

        String encodedPassword = passwordEncoder.encode(novaPassword);

        Utente utente = utenteRepository.findByEmail(email);
        if (utente != null) {
            utente.setPassword(encodedPassword);
            utenteRepository.save(utente);
            return ResponseEntity.ok("Password atualizada com sucesso para utente.");
        }

        Profissionais profissional = profissionaisRepository.findByEmail(email);
        if (profissional != null) {
            // usar query personalizada para evitar validação da cédula
            profissionaisRepository.atualizarPassword(email, encodedPassword);
            return ResponseEntity.ok("Password atualizada com sucesso para profissional.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Utilizador não encontrado.");
    }
}
