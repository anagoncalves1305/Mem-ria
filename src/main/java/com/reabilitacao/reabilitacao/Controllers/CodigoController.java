package com.reabilitacao.reabilitacao.Controllers;

import com.reabilitacao.reabilitacao.models.Codigo;
import com.reabilitacao.reabilitacao.service.CodigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/api/codigo")
@CrossOrigin(origins = "*")
public class CodigoController {

    @Autowired
    private CodigoService codigoService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> criarCodigo(@RequestBody Codigo codigo) {
        try {
            Codigo novo = codigoService.salvarCodigo(codigo);
            return ResponseEntity.status(HttpStatus.CREATED).body(novo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro ao salvar o código", "detalhes", e.getMessage()));
        }
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Codigo>> listarCodigo() {
        List<Codigo> lista = codigoService.listarCodigo();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/utente/{idUtente}")
    @ResponseBody
    public ResponseEntity<?> obterCodigoPorUtente(@PathVariable("idUtente") Integer id_utente) {
    	Optional<Codigo> codigo = codigoService.buscarPorIdUtente(id_utente);
        if (codigo.isPresent()) {
            return ResponseEntity.ok(codigo.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Código não encontrado"));
        }
    }

    @GetMapping(params = "valor")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> validarCodigoGet(@RequestParam("valor") String codigo) {
        Optional<Codigo> codigoEncontrado = codigoService.buscarPorCodigo(codigo);
        Map<String, Object> response = new HashMap<>();

        if (codigoEncontrado.isPresent()) {
            Codigo cod = codigoEncontrado.get();
            if (cod.getUtente() != null) {
                response.put("valido", true);
                response.put("idUtente", cod.getUtente().getId_utente());
            } else {
                response.put("valido", false);
            }
        } else {
            response.put("valido", false);
        }

        return ResponseEntity.ok(response);
    }
}
