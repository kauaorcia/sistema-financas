package com.kaua.demo.service;

import com.kaua.demo.model.Conta;
import com.kaua.demo.repository.ContaRepository;
import com.kaua.demo.security.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private AuthorizationService authorizationService;

    public List<Conta> listarPorUsuario(Long usuarioId) {
        authorizationService.requireUser(usuarioId);
        return contaRepository.findByUsuarioId(usuarioId);
    }

    public Conta buscarPorId(Long id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        authorizationService.requireAccountOwner(conta);
        return conta;
    }

    public Conta salvar(Conta conta) {
        conta.setUsuario(authorizationService.currentUser());
        return contaRepository.save(conta);
    }

    public void deletar(Long id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        authorizationService.requireAccountOwner(conta);
        contaRepository.delete(conta);
    }
}
