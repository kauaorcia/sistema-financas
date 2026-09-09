package com.kaua.demo.service;

import com.kaua.demo.dto.GastoPorCategoriaDTO;
import com.kaua.demo.dto.SaldoPorContaDTO;
import com.kaua.demo.model.Transacao;
import com.kaua.demo.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Transacao> listarPorConta(Long contaId) {
        return transacaoRepository.findByContaId(contaId);
    }

    public Transacao buscarPorId(Long id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
    }

    public Transacao salvar(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    public void deletar(Long id) {
        transacaoRepository.deleteById(id);
    }

    public List<GastoPorCategoriaDTO> gastoPorCategoria(Long contaId) {
        return transacaoRepository.gastoPorCategoria(contaId);
    }

    public List<SaldoPorContaDTO> saldoPorConta(Long usuarioId) {
        return transacaoRepository.saldoPorConta(usuarioId);
    }
}