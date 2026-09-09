package com.kaua.demo.controller;

import com.kaua.demo.dto.GastoPorCategoriaDTO;
import com.kaua.demo.dto.SaldoPorContaDTO;
import com.kaua.demo.model.Transacao;
import com.kaua.demo.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping("/conta/{contaId}")
    public List<Transacao> listarPorConta(@PathVariable Long contaId) {
        return transacaoService.listarPorConta(contaId);
    }

    @GetMapping("/{id}")
    public Transacao buscarPorId(@PathVariable Long id) {
        return transacaoService.buscarPorId(id);
    }

    @PostMapping
    public Transacao criar(@RequestBody Transacao transacao) {
        return transacaoService.salvar(transacao);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        transacaoService.deletar(id);
    }

    @GetMapping("/conta/{contaId}/relatorio/categoria")
    public List<GastoPorCategoriaDTO> gastoPorCategoria(@PathVariable Long contaId) {
        return transacaoService.gastoPorCategoria(contaId);
    }

    @GetMapping("/usuario/{usuarioId}/relatorio/saldo")
    public List<SaldoPorContaDTO> saldoPorConta(@PathVariable Long usuarioId) {
        return transacaoService.saldoPorConta(usuarioId);
    }
}