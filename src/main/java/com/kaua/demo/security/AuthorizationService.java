package com.kaua.demo.security;

import com.kaua.demo.model.Categoria;
import com.kaua.demo.model.Conta;
import com.kaua.demo.model.Transacao;
import com.kaua.demo.model.Usuario;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public Usuario currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof Usuario usuario)) {
            throw new AccessDeniedException("Usuário autenticado não encontrado");
        }
        return usuario;
    }

    public void requireUser(Long userId) {
        requireSameUser(userId, currentUser().getId());
    }

    public void requireAccountOwner(Conta conta) {
        if (conta == null || conta.getUsuario() == null) {
            throw new AccessDeniedException("Conta sem proprietário");
        }
        requireSameUser(conta.getUsuario().getId(), currentUser().getId());
    }

    public void requireCategoryOwner(Categoria categoria) {
        if (categoria == null || categoria.getUsuario() == null) {
            throw new AccessDeniedException("Categoria sem proprietário");
        }
        requireSameUser(categoria.getUsuario().getId(), currentUser().getId());
    }

    public void requireTransactionOwner(Transacao transacao) {
        if (transacao == null || transacao.getConta() == null) {
            throw new AccessDeniedException("Transação sem conta proprietária");
        }
        requireAccountOwner(transacao.getConta());
    }

    private void requireSameUser(Long requestedUserId, Long authenticatedUserId) {
        if (requestedUserId == null || authenticatedUserId == null
                || !requestedUserId.equals(authenticatedUserId)) {
            throw new AccessDeniedException("Recurso não pertence ao usuário autenticado");
        }
    }
}
