package com.kaua.demo.service;

import com.kaua.demo.dto.GastoPorCategoriaDTO;
import com.kaua.demo.dto.SaldoPorContaDTO;
import com.kaua.demo.model.Categoria;
import com.kaua.demo.model.Conta;
import com.kaua.demo.model.Transacao;
import com.kaua.demo.repository.CategoriaRepository;
import com.kaua.demo.repository.ContaRepository;
import com.kaua.demo.repository.TransacaoRepository;
import com.kaua.demo.security.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AuthorizationService authorizationService;

    public List<Transacao> listarPorConta(Long contaId) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        authorizationService.requireAccountOwner(conta);
        return transacaoRepository.findByContaId(contaId);
    }

    public Transacao buscarPorId(Long id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
        authorizationService.requireTransactionOwner(transacao);
        return transacao;
    }

    public Transacao salvar(Transacao transacao) {
        if (transacao.getConta() == null || transacao.getConta().getId() == null) {
            throw new IllegalArgumentException("Conta é obrigatória");
        }
        if (transacao.getCategoria() == null || transacao.getCategoria().getId() == null) {
            throw new IllegalArgumentException("Categoria é obrigatória");
        }

        Conta conta = contaRepository.findById(transacao.getConta().getId())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        Categoria categoria = categoriaRepository.findById(transacao.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        authorizationService.requireAccountOwner(conta);
        authorizationService.requireCategoryOwner(categoria);

        transacao.setConta(conta);
        transacao.setCategoria(categoria);
        return transacaoRepository.save(transacao);
    }

    public void deletar(Long id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
        authorizationService.requireTransactionOwner(transacao);
        transacaoRepository.delete(transacao);
    }

    public List<GastoPorCategoriaDTO> gastoPorCategoria(Long contaId) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        authorizationService.requireAccountOwner(conta);
        return transacaoRepository.gastoPorCategoria(contaId);
    }

    public List<SaldoPorContaDTO> saldoPorConta(Long usuarioId) {
        authorizationService.requireUser(usuarioId);
        return transacaoRepository.saldoPorConta(usuarioId);
    }
}
