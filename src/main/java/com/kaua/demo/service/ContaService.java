package com.kaua.demo.service;

import com.kaua.demo.model.Conta;
import com.kaua.demo.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public List<Conta> listarPorUsuario(Long usuarioId) {
        return contaRepository.findByUsuarioId(usuarioId);
    }

    public Conta buscarPorId(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

    public Conta salvar(Conta conta) {
        return contaRepository.save(conta);
    }

    public void deletar(Long id) {
        contaRepository.deleteById(id);
    }
}