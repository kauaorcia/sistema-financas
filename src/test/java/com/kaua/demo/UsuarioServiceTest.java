package com.kaua.demo;

import com.kaua.demo.controller.requests.UserRequest;
import com.kaua.demo.model.Usuario;
import com.kaua.demo.repository.UsuarioRepository;
import com.kaua.demo.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Kaua");
        usuario.setEmail("kaua@teste.com");
        usuario.setSenhaHash("senha123");
    }

    @Test
    void deveListarTodosOsUsuarios() {
        Usuario outroUsuario = new Usuario("Maria", "maria@teste.com", "outraSenha");
        List<Usuario> usuariosEsperados = List.of(usuario, outroUsuario);
        when(usuarioRepository.findAll()).thenReturn(usuariosEsperados);

        List<Usuario> resultado = usuarioService.listarTodos();

        assertSame(usuariosEsperados, resultado);
        assertEquals(2, resultado.size());
        verify(usuarioRepository).findAll();
        verifyNoMoreInteractions(usuarioRepository, passwordEncoder);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of());

        List<Usuario> resultado = usuarioService.listarTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(usuarioRepository).findAll();
        verifyNoMoreInteractions(usuarioRepository, passwordEncoder);
    }

    @Test
    void deveBuscarUsuarioPorIdQuandoExistir() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorId(1L);

        assertSame(usuario, resultado);
        verify(usuarioRepository).findById(1L);
        verifyNoMoreInteractions(usuarioRepository, passwordEncoder);
    }

    @Test
    void deveLancarExcecaoAoBuscarUsuarioInexistente() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(
                RuntimeException.class,
                () -> usuarioService.buscarPorId(99L)
        );

        assertEquals("Usuário não encontrado", excecao.getMessage());
        verify(usuarioRepository).findById(99L);
        verifyNoMoreInteractions(usuarioRepository, passwordEncoder);
    }

    @Test
    void deveSalvarNovoUsuarioComSenhaCodificada() {
        UserRequest requisicao = new UserRequest("Kaua", "kaua@teste.com", "senha123");
        when(usuarioRepository.findByEmail(requisicao.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(requisicao.senha())).thenReturn("senhaCodificada");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocacao -> {
            Usuario usuarioSalvo = invocacao.getArgument(0);
            usuarioSalvo.setId(1L);
            return usuarioSalvo;
        });

        Usuario resultado = usuarioService.salvar(requisicao);

        ArgumentCaptor<Usuario> usuarioCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).findByEmail("kaua@teste.com");
        verify(passwordEncoder).encode("senha123");
        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario enviadoParaSalvar = usuarioCaptor.getValue();
        assertAll(
                () -> assertEquals(1L, resultado.getId()),
                () -> assertEquals("Kaua", enviadoParaSalvar.getNome()),
                () -> assertEquals("kaua@teste.com", enviadoParaSalvar.getEmail()),
                () -> assertEquals("senhaCodificada", enviadoParaSalvar.getSenhaHash()),
                () -> assertSame(enviadoParaSalvar, resultado)
        );
        verifyNoMoreInteractions(usuarioRepository, passwordEncoder);
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaEstiverCadastrado() {
        UserRequest requisicao = new UserRequest("Outro nome", "kaua@teste.com", "novaSenha");
        when(usuarioRepository.findByEmail(requisicao.email())).thenReturn(Optional.of(usuario));

        RuntimeException excecao = assertThrows(
                RuntimeException.class,
                () -> usuarioService.salvar(requisicao)
        );

        assertEquals("Já existe um usuário com esse email", excecao.getMessage());
        verify(usuarioRepository).findByEmail("kaua@teste.com");
        verify(usuarioRepository, never()).save(any());
        verifyNoInteractions(passwordEncoder);
        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    void deveDeletarUsuarioPorId() {
        usuarioService.deletar(1L);

        verify(usuarioRepository).deleteById(1L);
        verifyNoMoreInteractions(usuarioRepository, passwordEncoder);
    }

}
