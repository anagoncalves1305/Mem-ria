package com.reabilitacao.reabilitacao.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.reabilitacao.reabilitacao.dto.MensagemDTO;
import com.reabilitacao.reabilitacao.models.MensagemChat;
import com.reabilitacao.reabilitacao.repository.MensagemRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*") 
public class ChatController {

    @Autowired
    private MensagemRepository mensagemRepository;

    @PostMapping
    public ResponseEntity<?> enviarMensagem(@RequestBody MensagemDTO mensagem) {
        MensagemChat nova = new MensagemChat();
        nova.setIdUtente(mensagem.getId_utente());
        nova.setIdProfissional(mensagem.getId_profissional());
        nova.setTexto(mensagem.getTexto());
        nova.setRemetente(mensagem.getRemetente());
        nova.setData(LocalDateTime.now());

        mensagemRepository.save(nova);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<MensagemChat>> getMensagens(
            @RequestParam int utente,
            @RequestParam int profissional
    ) {
        List<MensagemChat> mensagens = mensagemRepository.findByIdUtenteAndIdProfissionalOrderByDataAsc(utente, profissional);
        return ResponseEntity.ok(mensagens);
    }
    
    @GetMapping("/novas")
    public ResponseEntity<?> verificarMensagensNovas(
            @RequestParam int utente,
            @RequestParam int profissional,
            @RequestParam String remetente
    ) {
        int novas = mensagemRepository.countByIdUtenteAndIdProfissionalAndRemetenteAndLidaFalse(
            utente, profissional, remetente.toUpperCase()
        );

        Map<String, Integer> resposta = Map.of("novas", novas);
        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/marcar-lidas")
    public ResponseEntity<?> marcarMensagensComoLidas(
            @RequestParam int utente,
            @RequestParam int profissional,
            @RequestParam String remetente
    ) {
        List<MensagemChat> mensagens = mensagemRepository.findByIdUtenteAndIdProfissionalAndRemetenteAndLidaFalse(
            utente, profissional, remetente.toUpperCase()
        );

        for (MensagemChat m : mensagens) {
            m.setLida(true);
        }

        mensagemRepository.saveAll(mensagens);
        return ResponseEntity.ok().build();
    }


}
