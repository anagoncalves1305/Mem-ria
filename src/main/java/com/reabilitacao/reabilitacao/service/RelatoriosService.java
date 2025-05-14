package com.reabilitacao.reabilitacao.service;

import com.reabilitacao.reabilitacao.models.Relatorios;
import com.reabilitacao.reabilitacao.repository.RelatoriosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RelatoriosService {

    @Autowired
    private RelatoriosRepository relatoriosRepository;

    public Relatorios salvarRelatorios(Relatorios relatorios) {
        return relatoriosRepository.save(relatorios);
    }

    public List<Relatorios> listarRelatorios() {
    	 List<Relatorios>  relatorios = relatoriosRepository.findAll();
         System.out.println("Fetched Relatorios: " + relatorios);
         return relatorios;
    }

    public Optional<Relatorios> buscarPorId(Long id) {
        return relatoriosRepository.findById(id);
    }

	public List<Relatorios> buscarPorUtente(Long id_utente) {
		// TODO Auto-generated method stub
		return null;
	}
}
