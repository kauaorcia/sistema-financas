package com.kaua.demo.security;

import com.kaua.demo.model.Conta;
import com.kaua.demo.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthorizationServiceTest {

    private final AuthorizationService authorizationService = new AuthorizationService();

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void permiteRecursoDoUsuarioAutenticado() {
        Usuario usuario = new Usuario("A", "a@example.com", "hash");
        setAuthenticatedUser(usuario, 1L);
        assertDoesNotThrow(() -> authorizationService.requireUser(1L));
    }

    @Test
    void bloqueiaUsuarioDiferente() {
        Usuario usuario = new Usuario("A", "a@example.com", "hash");
        setAuthenticatedUser(usuario, 1L);
        assertThrows(AccessDeniedException.class, () -> authorizationService.requireUser(2L));
    }

    @Test
    void bloqueiaContaDeOutroUsuario() {
        Usuario autenticado = new Usuario("A", "a@example.com", "hash");
        setAuthenticatedUser(autenticado, 1L);
        Usuario outro = new Usuario("B", "b@example.com", "hash");
        Conta conta = new Conta();
        conta.setUsuario(outro);
        assertThrows(AccessDeniedException.class, () -> authorizationService.requireAccountOwner(conta));
    }

    private void setAuthenticatedUser(Usuario usuario, Long id) {
        usuario.setId(id);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()));
    }
}
