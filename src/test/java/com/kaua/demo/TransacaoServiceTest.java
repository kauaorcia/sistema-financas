package com.kaua.demo;

import com.kaua.demo.model.Transacao;
import com.kaua.demo.repository.TransacaoRepository;
import com.kaua.demo.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private TransacaoService transacaoService;

    private Transacao transacao;

    @BeforeEach
    void setUp() {
        transacao = new Transacao();
        transacao.setId(1L);
        transacao.setValor(new BigDecimal("150.50"));
        transacao.setData(LocalDate.now());
        transacao.setDescricao("Supermercado");
    }

    @Test
    void deveRetornarTransacaoQuandoIdExiste() {
        when(transacaoRepository.findById(1L)).thenReturn(Optional.of(transacao));

        Transacao resultado = transacaoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(new BigDecimal("150.50"), resultado.getValor());
        verify(transacaoRepository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoTransacaoNaoExiste() {
        when(transacaoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException excecao = assertThrows(RuntimeException.class, () -> {
            transacaoService.buscarPorId(99L);
        });

        assertEquals("Transação não encontrada", excecao.getMessage());
    }

    @Test
    void deveSalvarTransacaoComValorCorreto() {
        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);

        Transacao resultado = transacaoService.salvar(transacao);

        assertNotNull(resultado);
        assertEquals(new BigDecimal("150.50"), resultado.getValor());
        verify(transacaoRepository).save(transacao);
    }
}