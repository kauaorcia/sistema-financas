package com.kaua.demo.repository;

import com.kaua.demo.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findByUsuarioId(Long usuarioId);
}