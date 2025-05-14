package com.reabilitacao.reabilitacao.Controllers;

import com.reabilitacao.reabilitacao.models.Profissionais;
import com.reabilitacao.reabilitacao.service.ProfissionaisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/api/profissionais")
@CrossOrigin(origins = "*")
public class ProfissionaisController {

    @Autowired
    private ProfissionaisService profissionaisService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<Profissionais> criarProfissional(@RequestBody Profissionais profissionais) {
        Profissionais novo = profissionaisService.salvarProfissionais(profissionais);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
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
