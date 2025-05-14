package com.reabilitacao.reabilitacao.repository;

import com.reabilitacao.reabilitacao.models.Codigo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CodigoRepository extends JpaRepository<Codigo, Integer> {

	Optional<Codigo> findByCodigo(@Param("codigo") String codigo);

	Optional<Codigo> findByUtenteId(Integer idUtente);
}

