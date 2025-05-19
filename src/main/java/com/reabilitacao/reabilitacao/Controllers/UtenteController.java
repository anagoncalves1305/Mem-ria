package com.reabilitacao.reabilitacao.Controllers;

import com.reabilitacao.reabilitacao.models.Utente;
import com.reabilitacao.reabilitacao.service.EmailService;
import com.reabilitacao.reabilitacao.service.UtenteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/api/utentes")
@CrossOrigin(origins = "*") // Permite CORS de qualquer origem
public class UtenteController {

    @Autowired
    private UtenteService utenteService;
    
    @Autowired
    private EmailService emailService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> criarUtente(@RequestBody Utente utente) {
        String token = UUID.randomUUID().toString();
        utente.setTokenValidacao(token);
        utente.setEmailVerificado(false);

        Utente novo = utenteService.salvarUtente(utente);

        try {
            emailService.enviarEmailConfirmacao(novo.getEmail(), token);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro ao enviar email de confirmação."));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensagem", "Utente criado com sucesso. Confirma o email antes de iniciar sessão."));
    }


    // Listar todos os utentes
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Utente>> listarUtentes(@RequestParam(required = false) Integer id_profissional) {
        List<Utente> lista;

        if (id_profissional != null) {
            lista = utenteService.listarPorProfissional(id_profissional);
        } else {
            lista = utenteService.listarUtentes();
        }

        return ResponseEntity.ok(lista);
    }


    // Obter um utente específico por ID
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> obterUtente(@PathVariable Long id) {
        Optional<Utente> utente = utenteService.buscarPorId(id);
        if (utente.isPresent()) {
            return ResponseEntity.ok(utente.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Utente não encontrado"));
        }
    }

    // Associar um profissional a um utente
    @PutMapping("/associar")
    @ResponseBody
    public ResponseEntity<?> associarProfissional(
    		 @RequestParam Long idUtente,
            @RequestParam Integer idProfissional) {
    
        boolean sucesso = utenteService.associarProfissional(idUtente, idProfissional);
        System.out.println(sucesso);
        if (sucesso) {
            return ResponseEntity.ok(Map.of("mensagem", "Profissional associado com sucesso"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", "Não foi possível associar o profissional"));
        }
    }

}
