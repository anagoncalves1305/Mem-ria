package com.reabilitacao.reabilitacao.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.reabilitacao.reabilitacao.models.Consulta;
import com.reabilitacao.reabilitacao.models.MensagemChat;
import com.reabilitacao.reabilitacao.dto.ConsultaDTO;
import com.reabilitacao.reabilitacao.repository.ConsultaRepository;
import com.reabilitacao.reabilitacao.repository.MensagemRepository;
import com.reabilitacao.reabilitacao.service.ConsultaService;

@RestController
@RequestMapping("/api/consultas")
@CrossOrigin(origins = "*")
public class ConsultaController {

	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private MensagemRepository mensagemRepository;
	
    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    public ResponseEntity<?> criarConsulta(@RequestBody ConsultaDTO dto) {
        consultaService.agendarConsulta(dto);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/proximas")
    public ResponseEntity<List<Consulta>> getConsultasAceites(@RequestParam int utente) {
        List<Consulta> consultas = consultaRepository.findByIdUtenteAndEstadoOrderByDataAsc(utente, "aceite");
        return ResponseEntity.ok(consultas);
    }
    
    @GetMapping("/pendentes")
    public ResponseEntity<List<Consulta>> getConsultasPendentes(@RequestParam int utente) {
        List<Consulta> consultas = consultaRepository.findByIdUtenteAndEstadoOrderByDataAsc(utente, "pendente");
        return ResponseEntity.ok(consultas);
    }
    
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> atualizarEstado(@PathVariable Integer id, @RequestParam String estado) {
        Consulta consulta = consultaRepository.findById(id).orElse(null);
        if (consulta == null) {
            return ResponseEntity.notFound().build();
        }

        consulta.setEstado(estado);
        consultaRepository.save(consulta);

        // Prepara a mensagem a ser enviada para o chat
        String simbolo;
        if (estado.equalsIgnoreCase("aceite")) {
            simbolo = "✅";  // Aceite
        } else if (estado.equalsIgnoreCase("recusada")) {
            simbolo = "❌";  // Recusada
        } else {
            simbolo = "⚠️";  // Para outros estados não tratados
        }        String data = consulta.getData().toLocalDate().toString();
        String hora = consulta.getData().toLocalTime().toString().substring(0, 5);

        String texto = simbolo + " A sua consulta para " + data + " às " + hora + " foi " + estado + ".";

        // Envia a mensagem para o chat
        mensagemRepository.save(new MensagemChat(consulta.getIdUtente(), consulta.getIdProfissional(), texto, "profissional"));

        return ResponseEntity.ok().build();
    }
}