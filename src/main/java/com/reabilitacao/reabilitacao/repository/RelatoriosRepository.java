package com.reabilitacao.reabilitacao.repository;

import com.reabilitacao.reabilitacao.models.Relatorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatoriosRepository extends JpaRepository<Relatorios, Long> {
}