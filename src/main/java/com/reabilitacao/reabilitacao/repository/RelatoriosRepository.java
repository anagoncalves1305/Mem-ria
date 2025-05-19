package com.reabilitacao.reabilitacao.repository;

import com.reabilitacao.reabilitacao.models.Relatorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatoriosRepository extends JpaRepository<Relatorios, Long> {

    @Query("SELECT r FROM Relatorios r WHERE r.id_utente = :idUtente")
    List<Relatorios> buscarPorIdUtente(@Param("idUtente") Integer idUtente);
}
