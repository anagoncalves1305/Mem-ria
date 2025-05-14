package com.reabilitacao.reabilitacao.Controllers;

import com.reabilitacao.reabilitacao.models.Relatorios;
import com.reabilitacao.reabilitacao.service.RelatoriosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/api/relatorios")
@CrossOrigin(origins = "*")
public class RelatoriosController {

    @Autowired
    private RelatoriosService relatoriosService;

    // Criar novo relatório
    @PostMapping
    @ResponseBody
    public ResponseEntity<Relatorios> criarRelatorios(@RequestBody Relatorios relatorio) {
        Relatorios novo = relatoriosService.salvarRelatorios(relatorio);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    // Listar todos os relatórios
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Relatorios>> listarRelatorios() {
        List<Relatorios> lista = relatoriosService.listarRelatorios();
        return ResponseEntity.ok(lista);
    }

    // Buscar relatório por ID
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> obterRelatorios(@PathVariable Long id) {
        Optional<Relatorios> relatorio = relatoriosService.buscarPorId(id);
        if (relatorio.isPresent()) {
            return ResponseEntity.ok(relatorio.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Relatório não encontrado"));
        }
    }

    // 🔥 Buscar todos os relatórios por ID do utente
    @GetMapping("/utente/{idUtente}")
    @ResponseBody
    public ResponseEntity<List<Relatorios>> relatoriosPorUtente(@PathVariable Long id_utente) {
        List<Relatorios> relatorios = relatoriosService.buscarPorUtente(id_utente);
        return ResponseEntity.ok(relatorios);
    }

}

