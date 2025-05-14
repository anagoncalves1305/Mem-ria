package com.reabilitacao.reabilitacao.service;

import com.reabilitacao.reabilitacao.models.Partidas;
import com.reabilitacao.reabilitacao.repository.PartidasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartidasService {

    @Autowired
    private PartidasRepository partidasRepository;

    public Partidas salvaPartidas(Partidas partidas) {
        return partidasRepository.save(partidas);
    }

    public List<Partidas> listarPartidas() {
    	 List<Partidas> partidas = partidasRepository.findAll();
         System.out.println("Fetched Partidas: " + partidas);
         return partidas;
    }

    public Optional<Partidas> buscarPorId(Long id) {
        return partidasRepository.findById(id);
    }
    
    public Integer calcularTotalEstrelasUtente(Integer idUtente) {
        return partidasRepository.somarEstrelasPorUtente(idUtente);
    }
}
