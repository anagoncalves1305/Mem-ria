package com.reabilitacao.reabilitacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reabilitacao.reabilitacao.dto.ConsultaDTO;
import com.reabilitacao.reabilitacao.models.Consulta;
import com.reabilitacao.reabilitacao.repository.ConsultaRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ConsultaService {

	@Autowired
	private ConsultaRepository consultaRepository;

	public List<Consulta> getConsultasAceitesPorUtente(int idUtente) {
	    return consultaRepository.findByIdUtenteAndEstadoOrderByDataAsc(idUtente, "aceite");
	}
    public void agendarConsulta(ConsultaDTO dto) {
        Consulta consulta = new Consulta();
        consulta.setIdUtente(dto.getId_utente());
        consulta.setIdProfissional(dto.getId_profissional());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dto.getData(), formatter);
        consulta.setData(dataHora);

        consulta.setEstado(dto.getEstado() != null ? dto.getEstado() : "pendente");

        consultaRepository.save(consulta);
    }
}
