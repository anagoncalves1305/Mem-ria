package com.reabilitacao.reabilitacao.service;

import com.reabilitacao.reabilitacao.models.Codigo;
import com.reabilitacao.reabilitacao.repository.CodigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CodigoService {

    @Autowired
    private CodigoRepository codigoRepository;

    public Codigo salvarCodigo(Codigo codigo) {
        return codigoRepository.save(codigo);
    }

    public List<Codigo> listarCodigo() {
        return codigoRepository.findAll();
    }

    public Optional<Codigo> buscarPorId(Long id) {
        return codigoRepository.findById(id.intValue());
    }

    public Optional<Codigo> buscarPorCodigo(String codigo) {
        return codigoRepository.findByCodigo(codigo);
    }

    public boolean codigoExiste(String codigoStr) {
        return codigoRepository.findByCodigo(codigoStr).isPresent();
    }

    public Optional<Codigo> buscarPorId(Integer id_utente) {
        return Optional.empty();
    }

    public Optional<Codigo> buscarPorIdUtente(Integer idUtente) {
        return codigoRepository.findByUtenteId(idUtente);
    }
}

