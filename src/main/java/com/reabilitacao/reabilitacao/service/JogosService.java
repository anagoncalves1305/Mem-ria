package com.reabilitacao.reabilitacao.service;

import com.reabilitacao.reabilitacao.models.Jogos;
import com.reabilitacao.reabilitacao.repository.JogosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JogosService {

    @Autowired
    private JogosRepository jogosRepository;

    public Jogos salvarJogos(Jogos jogos) {
        return jogosRepository.save(jogos);
    }

    public List<Jogos> listarJogos() {
    	 List<Jogos> jogos = jogosRepository.findAll();
         System.out.println("Fetched Jogos: " + jogos);
         return jogos;
    }

    public Optional<Jogos> buscarPorId(Long id) {
        return jogosRepository.findById(id);
    }
}
