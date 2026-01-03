package com.itsqmet.service;

import com.itsqmet.model.Solicitud;
import com.itsqmet.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    // Solo muestra solicitudes del usuario logueado
    public List<Solicitud> mostrarSolicitudesPorUsuario(Long usuarioId) {
        return solicitudRepository.findByUsuarioId(usuarioId);
    }

    //Buscar por id
    public Optional<Solicitud> buscarSolicitudById (Long id){
        return solicitudRepository.findById(id);
    }

    //Guardar
    public Solicitud guardarSolicitud(Solicitud solicitud) {
        return solicitudRepository.save(solicitud);
    }

    //Actualizar
    public Solicitud actualizarSolicitud(Long id, Solicitud solicitud){
        Solicitud solicitudExiste = buscarSolicitudById(id)
                .orElseThrow(()-> new RuntimeException("Solicitud no existe"));
        solicitudExiste.setNombres(solicitud.getNombres());
        solicitudExiste.setApellidos(solicitud.getApellidos());
        solicitudExiste.setFechaDesde(solicitud.getFechaDesde());
        solicitudExiste.setFechaHasta(solicitud.getFechaHasta());
        solicitudExiste.setEmail(solicitud.getEmail());
        solicitudExiste.setDescripcion(solicitud.getDescripcion());
        return solicitudRepository.save(solicitudExiste);
    }

    //Eliminar
    public void eliminarSolicitud(Long id){
        Solicitud solicitud = buscarSolicitudById(id)
                //se lanza cuando no encuentra el producto
                .orElseThrow(() -> new ResponseStatusException(
                        //constante de spring que representa el codigo http 404
                        //404=recurso no encontrado
                        HttpStatus.NOT_FOUND, "Solicitud no existe"
                ));
        solicitudRepository.delete(solicitud);
    }


}
