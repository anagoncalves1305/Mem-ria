package com.reabilitacao.reabilitacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.reabilitacao.reabilitacao.models.MensagemChat;

import java.util.List;

public interface MensagemRepository extends JpaRepository<MensagemChat, Integer> {
	List<MensagemChat> findByIdUtenteAndIdProfissionalOrderByDataAsc(@Param("idUtente") int utente, @Param("idProfissional") int profissional);
    
 // Conta mensagens não lidas de um remetente para um destinatário
    int countByIdUtenteAndIdProfissionalAndRemetenteAndLidaFalse(int idUtente, int idProfissional, String remetente);

    // Vai buscar mensagens não lidas de um remetente para um destinatário
    List<MensagemChat> findByIdUtenteAndIdProfissionalAndRemetenteAndLidaFalse(int idUtente, int idProfissional, String remetente);
}
