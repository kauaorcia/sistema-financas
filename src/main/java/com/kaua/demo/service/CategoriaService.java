package com.kaua.demo.service;

import com.kaua.demo.model.Categoria;
import com.kaua.demo.repository.CategoriaRepository;
import com.kaua.demo.security.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AuthorizationService authorizationService;

    public List<Categoria> listarPorUsuario(Long usuarioId) {
        authorizationService.requireUser(usuarioId);
        return categoriaRepository.findByUsuarioId(usuarioId);
    }

    public Categoria buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        authorizationService.requireCategoryOwner(categoria);
        return categoria;
    }

    public Categoria salvar(Categoria categoria) {
        categoria.setUsuario(authorizationService.currentUser());
        return categoriaRepository.save(categoria);
    }

    public void deletar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        authorizationService.requireCategoryOwner(categoria);
        categoriaRepository.delete(categoria);
    }
}
