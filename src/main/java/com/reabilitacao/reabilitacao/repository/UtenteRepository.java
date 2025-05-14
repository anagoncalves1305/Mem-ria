package com.reabilitacao.reabilitacao.repository;

import com.reabilitacao.reabilitacao.models.Utente;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

	Utente findByEmail(String email);
	@Query("SELECT u FROM Utente u WHERE u.id_profissional = :idProfissional")
    List<Utente> findByIdProfissional(@Param("idProfissional") Integer idProfissional);
	Utente findByTokenValidacao(String token);
	


}
