package com.kaua.demo.controller;

import com.kaua.demo.model.Conta;
import com.kaua.demo.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @GetMapping("/usuario/{usuarioId}")
    public List<Conta> listarPorUsuario(@PathVariable Long usuarioId) {
        return contaService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/{id}")
    public Conta buscarPorId(@PathVariable Long id) {
        return contaService.buscarPorId(id);
    }

    @PostMapping
    public Conta criar(@RequestBody Conta conta) {
        return contaService.salvar(conta);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        contaService.deletar(id);
    }
}