package com.itsqmet.service;

import com.itsqmet.model.Permiso;
import com.itsqmet.model.Solicitud;
import com.itsqmet.repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    public List<Permiso> mostrarPermiso(Long usuarioId){
        return permisoRepository.findByUsuarioId(usuarioId);
    }

    //Buscar por id
    public Optional<Permiso> buscarPermisoById (Long id){
        return permisoRepository.findById(id);
    }

    //Guardar
    public Permiso guardarPermiso(Permiso permiso){
        permisoRepository.save(permiso);
        return permiso;
    }

    //Actualizar
    public Permiso actualizarPermiso(Long id, Permiso permiso){
        Permiso permisoExiste = buscarPermisoById(id)
                .orElseThrow(()-> new RuntimeException("Permiso no existe"));
        permisoExiste.setNombres(permiso.getNombres());
        permisoExiste.setApellidos(permiso.getApellidos());
        permisoExiste.setFechaDesde(permiso.getFechaDesde());
        permisoExiste.setFechaHasta(permiso.getFechaHasta());
        permisoExiste.setEmail(permiso.getEmail());
        permisoExiste.setDescripcion(permiso.getDescripcion());
        return permisoRepository.save(permisoExiste);
    }

    //Eliminar
    public void eliminarPermiso(Long id){
        Permiso permiso = buscarPermisoById(id)
                //se lanza cuando no encuentra el producto
                .orElseThrow(() -> new ResponseStatusException(
                        //constante de spring que representa el codigo http 404
                        //404=recurso no encontrado
                        HttpStatus.NOT_FOUND, "Permiso no existe"
                ));
        permisoRepository.delete(permiso);
    }

}
