package com.itsqmet.service;

import com.itsqmet.model.Permiso;
import com.itsqmet.model.Solicitud;
import com.itsqmet.repository.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    public List<Permiso> mostrarPermiso(){
        return permisoRepository.findAll();
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

}
