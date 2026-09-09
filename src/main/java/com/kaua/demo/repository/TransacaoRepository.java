package com.kaua.demo.repository;

import com.kaua.demo.dto.GastoPorCategoriaDTO;
import com.kaua.demo.dto.SaldoPorContaDTO;
import com.kaua.demo.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByContaId(Long contaId);

    @Query("SELECT new com.kaua.demo.dto.GastoPorCategoriaDTO(c.nome, SUM(t.valor)) " +
            "FROM Transacao t JOIN t.categoria c " +
            "WHERE t.conta.id = :contaId " +
            "GROUP BY c.nome")
    List<GastoPorCategoriaDTO> gastoPorCategoria(Long contaId);

    @Query("SELECT new com.kaua.demo.dto.SaldoPorContaDTO(c.id, c.nome, " +
            "SUM(CASE WHEN cat.tipo = 'RECEITA' THEN t.valor ELSE -t.valor END)) " +
            "FROM Conta c JOIN c.usuario u " +
            "JOIN Transacao t ON t.conta = c " +
            "JOIN t.categoria cat " +
            "WHERE u.id = :usuarioId " +
            "GROUP BY c.id, c.nome")
    List<SaldoPorContaDTO> saldoPorConta(Long usuarioId);

}