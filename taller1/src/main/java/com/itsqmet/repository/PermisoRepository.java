package com.itsqmet.repository;

import com.itsqmet.model.Gastos;
import com.itsqmet.model.Permiso;
import com.itsqmet.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    List<Permiso> findByUsuarioId(Long usuarioId);
}
