package com.itsqmet.repository;

import com.itsqmet.model.Gastos;
import com.itsqmet.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GastosRepository extends JpaRepository <Gastos, Long> {
    List<Gastos> findByUsuarioId(Long usuarioId);
    Optional<Gastos> findByUsuarioIdAndPeriodo(Long usuarioId, Integer periodo);
}
