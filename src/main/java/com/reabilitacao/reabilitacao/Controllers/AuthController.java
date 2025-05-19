package com.reabilitacao.reabilitacao.Controllers;

import com.reabilitacao.reabilitacao.models.Profissionais;
import com.reabilitacao.reabilitacao.models.Utente;
import com.reabilitacao.reabilitacao.repository.ProfissionaisRepository;
import com.reabilitacao.reabilitacao.repository.UtenteRepository;
import com.reabilitacao.reabilitacao.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class AuthController {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private ProfissionaisRepository profissionaisRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, String>> login(@RequestParam String email, @RequestParam String password) {
        Utente utente = utenteRepository.findByEmail(email);
        if (utente != null && passwordEncoder.matches(password, utente.getPassword())) {
            if (!utente.isEmailVerificado()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Email ainda não foi confirmado."));
            }
            return ResponseEntity.ok(Map.of(
                    "id", String.valueOf(utente.getId()),
                    "nome", utente.getNome(),
                    "role", "utente"));
        }

        Profissionais profissional = profissionaisRepository.findByEmail(email);
        if (profissional != null && passwordEncoder.matches(password, profissional.getPassword())) {
            if (!profissional.isEmailVerificado()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Email ainda não foi confirmado."));
            }
            return ResponseEntity.ok(Map.of(
                    "id", String.valueOf(profissional.getId()),
                    "nome", profissional.getNome(),
                    "role", "profissional"));
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
            if (profissional != null) {
                return ResponseEntity.ok("dados_validados");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Dados incorretos. Verifica o email e o número fornecido.");
    }

    @PostMapping("/atualizar-password")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> atualizarPassword(@RequestParam String email, @RequestParam String novaPassword) {
        if (novaPassword == null || novaPassword.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nova password inválida.");
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
            profissional.setPassword(encodedPassword);
            profissionaisRepository.save(profissional);
            return ResponseEntity.ok("Password atualizada com sucesso para profissional.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizador não encontrado.");
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/enviar-token-alterar-password")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> enviarTokenAlterarPassword(@RequestParam String email) {
        String token = UUID.randomUUID().toString();

        Utente utente = utenteRepository.findByEmail(email);
        if (utente != null) {
            utente.setTokenValidacao(token);
            utenteRepository.save(utente);
            try {
                emailService.enviarEmailAlteracaoPassword(email, token);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar email.");
            }
            return ResponseEntity.ok("Token enviado para o email do utente.");
        }

        Profissionais profissional = profissionaisRepository.findByEmail(email);
        if (profissional != null) {
            profissional.setTokenValidacao(token);
            profissionaisRepository.save(profissional);
            try {
                emailService.enviarEmailAlteracaoPassword(email, token);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar email.");
            }
            return ResponseEntity.ok("Token enviado para o email do profissional.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email não encontrado.");
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/confirmar-token-alteracao")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> confirmarTokenAlteracao(@RequestParam String token, @RequestParam String novaPassword) {
        if (novaPassword == null || novaPassword.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password inválida.");
        }

        String encodedPassword = passwordEncoder.encode(novaPassword);

        Utente utente = utenteRepository.findByTokenValidacao(token);
        if (utente != null) {
            utente.setPassword(encodedPassword);
            utente.setTokenValidacao(null);
            utenteRepository.save(utente);
            return ResponseEntity.ok("Password atualizada com sucesso para utente.");
        }

        Profissionais profissional = profissionaisRepository.findByTokenValidacao(token);
        if (profissional != null) {
            profissional.setPassword(encodedPassword);
            profissional.setTokenValidacao(null);
            profissionaisRepository.save(profissional);
            return ResponseEntity.ok("Password atualizada com sucesso para profissional.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido.");
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/confirmar-email")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> confirmarEmail(@RequestParam String token) {
        String mensagem;

        Utente utente = utenteRepository.findByTokenValidacao(token);
        if (utente != null) {
            utente.setEmailVerificado(true);
            utente.setTokenValidacao(null);
            utenteRepository.save(utente);
            mensagem = "✅ Email de utente confirmado com sucesso.";
        } else {
            Profissionais profissional = profissionaisRepository.findByTokenValidacao(token);
            if (profissional != null) {
                profissional.setEmailVerificado(true);
                profissional.setTokenValidacao(null);
                profissionaisRepository.save(profissional);
                mensagem = "✅ Email de profissional confirmado com sucesso.";
            } else {
                mensagem = "❌ Token inválido ou já usado.";
            }
        }

        String html = "<!DOCTYPE html>" +
                "<html lang='pt'><head><meta charset='UTF-8'>" +
                "<title>Confirmação de Email</title>" +
                "<style>" +
                "body { background-color: #e4f0ff; font-family: 'Segoe UI', sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }" +
                ".box { background-color: #d9ecff; padding: 30px; border-radius: 12px; text-align: center; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }" +
                ".box h2 { margin-bottom: 20px; font-size: 22px; }" +
                ".btn { background-color: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 6px; font-weight: bold; transition: 0.3s; }" +
                ".btn:hover { background-color: #0056b3; }" +
                "</style></head><body>" +
                "<div class='box'>" +
                "<h2>" + mensagem + "</h2>" +
                "<a class='btn' href='http://localhost:8080/login'>Voltar ao site</a>" +
                "</div></body></html>";

        return ResponseEntity.ok().header("Content-Type", "text/html").body(html);
    }
}
