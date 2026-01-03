package com.itsqmet.repository;

import com.itsqmet.model.Gastos;
import com.itsqmet.model.Permiso;
import com.itsqmet.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    // Buscar todas las solicitudes que pertenecen a un ID de usuario específico
    List<Solicitud> findByUsuarioId(Long usuarioId);
}