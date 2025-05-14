package com.reabilitacao.reabilitacao.repository;

import com.reabilitacao.reabilitacao.models.Partidas;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidasRepository extends JpaRepository<Partidas, Long> {
	@Query("SELECT COALESCE(SUM(p.estrelas_ganhas), 0) FROM Partidas p WHERE p.id_utente = :idUtente")
	Integer somarEstrelasPorUtente(@Param("idUtente") Integer idUtente);


}
