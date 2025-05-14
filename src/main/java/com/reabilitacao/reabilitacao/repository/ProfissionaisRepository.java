package com.reabilitacao.reabilitacao.repository;

import com.reabilitacao.reabilitacao.models.Profissionais;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionaisRepository extends JpaRepository<Profissionais, Long> {

	Profissionais findByEmail(String email);

	Profissionais findByTokenValidacao(String token);
	@Modifying
	@Query("UPDATE Profissionais p SET p.password = :password WHERE p.email = :email")
	void atualizarPassword(@Param("email") String email, @Param("password") String password);




	
}
