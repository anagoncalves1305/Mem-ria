package com.reabilitacao.reabilitacao.Controllers;

import com.reabilitacao.reabilitacao.models.Profissionais;
import com.reabilitacao.reabilitacao.service.EmailService;
import com.reabilitacao.reabilitacao.service.ProfissionaisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/api/profissionais")
@CrossOrigin(origins = "*")
public class ProfissionaisController {

    @Autowired
    private ProfissionaisService profissionaisService;
    
    @Autowired
    private EmailService emailService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> criarProfissional(@RequestBody Profissionais profissional) {
        String token = UUID.randomUUID().toString();
        profissional.setTokenValidacao(token);
        profissional.setEmailVerificado(false);

        Profissionais novo = profissionaisService.salvarProfissionais(profissional);

        try {
            emailService.enviarEmailConfirmacao(novo.getEmail(), token);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro ao enviar email de confirmação."));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensagem", "Profissional criado com sucesso. Confirma o email antes de iniciar sessão."));
    }


    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Profissionais>> listarProfissionais() {
        List<Profissionais> lista = profissionaisService.listarProfissionais();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> obterProfissional(@PathVariable Long id) {
        Optional<Profissionais> profissional = profissionaisService.buscarPorId(id);
        if (profissional.isPresent()) {
            return ResponseEntity.ok(profissional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Profissional não encontrado"));
        }
    }

}
