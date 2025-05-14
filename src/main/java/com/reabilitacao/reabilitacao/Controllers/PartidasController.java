package com.reabilitacao.reabilitacao.Controllers;

import com.reabilitacao.reabilitacao.models.Partidas;
import com.reabilitacao.reabilitacao.service.PartidasService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/api/partidas")
@CrossOrigin(origins = "*")
public class PartidasController {

    @Autowired
    private PartidasService partidasService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<Partidas> criarPartidas(@RequestBody Partidas partidas) {
        Partidas nova = partidasService.salvaPartidas(partidas);
        return ResponseEntity.status(HttpStatus.CREATED).body(nova);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Partidas>> listarPartidas() {
        List<Partidas> lista = partidasService.listarPartidas();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> obterPartida(@PathVariable Long id) {
        Optional<Partidas> partida = partidasService.buscarPorId(id);
        if (partida.isPresent()) {
            return ResponseEntity.ok(partida.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Partida não encontrada"));
        }
    }
    
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> atualizarPartida(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Optional<Partidas> partidaOpt = partidasService.buscarPorId(id);
        if (partidaOpt.isPresent()) {
            Partidas partida = partidaOpt.get();

            if (body.containsKey("tempo")) {
                partida.setTempo((Integer) body.get("tempo"));
            }

            if (body.containsKey("usou_ajuda")) {
                partida.setUsou_ajuda((Boolean) body.get("usou_ajuda"));
            }

            if (body.containsKey("estrelas_ganhas")) {
                partida.setEstrelas_ganhas((Integer) body.get("estrelas_ganhas"));
            }
            
            if (body.containsKey("nivel")) {
                Object nivelObj = body.get("nivel");
                if (nivelObj instanceof Integer) {
                    partida.setNivel((Integer) nivelObj);
                } else if (nivelObj instanceof String) {
                    // Caso venha como string
                    partida.setNivel(Integer.parseInt((String) nivelObj));
                }
            }
            
            if (body.containsKey("data_jogo")) {
                Object dataStr = body.get("data_jogo");
                if (dataStr != null && dataStr instanceof String) {
                    try {
                        String dataTexto = ((String) dataStr).substring(0, 19); // corta milissegundos
                        partida.setData_jogo(LocalDateTime.parse(dataTexto));
                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Erro ao converter data_jogo: " + e.getMessage());
                    }
                }
            }

            partidasService.salvaPartidas(partida);
            return ResponseEntity.ok(partida);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Partida não encontrada.");
        }
    }

    @GetMapping("/utentes/{idUtente}/estrelas")
    @ResponseBody
    public ResponseEntity<?> getTotalEstrelas(@PathVariable Integer idUtente) {
        Integer total = partidasService.calcularTotalEstrelasUtente(idUtente);
        return ResponseEntity.ok(Map.of("estrelas", total));
    }
    
    

    
}
