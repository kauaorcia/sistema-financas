package com.kaua.demo.controller;

import com.kaua.demo.model.Categoria;
import com.kaua.demo.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/usuario/{usuarioId}")
    public List<Categoria> listarPorUsuario(@PathVariable Long usuarioId) {
        return categoriaService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/{id}")
    public Categoria buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id);
    }

    @PostMapping
    public Categoria criar(@RequestBody Categoria categoria) {
        return categoriaService.salvar(categoria);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
    }
}