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
}