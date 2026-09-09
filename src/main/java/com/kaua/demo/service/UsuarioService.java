package com.kaua.demo.service;

import com.kaua.demo.controller.requests.UserRequest;
import com.kaua.demo.model.Usuario;
import com.kaua.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario salvar(UserRequest usuario) {
        if (usuarioRepository.findByEmail(usuario.email()).isPresent()) {
            throw new RuntimeException("Já existe um usuário com esse email");
        }
        var user = new Usuario(usuario.nome(), usuario.email(), passwordEncoder.encode(usuario.senha()));

        return usuarioRepository.save(user);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}

