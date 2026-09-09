package com.kaua.demo.controller;

import com.kaua.demo.controller.requests.UserRequest;
import com.kaua.demo.dto.UserResponse;
import com.kaua.demo.model.Usuario;
import com.kaua.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<UserResponse> criar(@RequestBody UserRequest usuario) {
        var user = usuarioService.salvar(usuario);
        var response = new UserResponse(user.getId(), user.getNome(), user.getEmail());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
    }
}