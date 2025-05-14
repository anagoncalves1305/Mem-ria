package com.reabilitacao.reabilitacao.Controllers;

import com.reabilitacao.reabilitacao.models.Jogos;
import com.reabilitacao.reabilitacao.service.JogosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/api/jogos")
@CrossOrigin(origins = "*")
public class JogosController {

    @Autowired
    private JogosService jogosService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<Jogos> criarJogos(@RequestBody Jogos jogo) {
        Jogos novo = jogosService.salvarJogos(jogo);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Jogos>> listarJogos() {
        List<Jogos> lista = jogosService.listarJogos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> obterJogos(@PathVariable Long id) {
        Optional<Jogos> jogo = jogosService.buscarPorId(id);
        if (jogo.isPresent()) {
            return ResponseEntity.ok(jogo.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Jogo não encontrado"));
        }
    }
}
