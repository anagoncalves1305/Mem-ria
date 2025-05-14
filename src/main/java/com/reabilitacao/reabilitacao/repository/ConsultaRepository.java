package com.reabilitacao.reabilitacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reabilitacao.reabilitacao.models.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
	List<Consulta> findByIdUtenteAndEstadoOrderByDataAsc(int idUtente, String estado);

}
