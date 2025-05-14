package com.reabilitacao.reabilitacao.repository;

import com.reabilitacao.reabilitacao.models.Jogos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogosRepository extends JpaRepository<Jogos, Long> {
}