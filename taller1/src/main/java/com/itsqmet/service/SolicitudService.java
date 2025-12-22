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

    //leer
    public List<Solicitud> mostrarSolicitud(){

        return solicitudRepository.findAll();
    }

    //Buscar por id
    public Optional<Solicitud> buscarSolicitudById (Long id){
        return solicitudRepository.findById(id);
    }

    //BUSCAR POR NOMBRES
    public List<Solicitud> buscarSolicitudPorNombre(String buscarSolicitud){
        if(buscarSolicitud== null || buscarSolicitud.isEmpty()){
            return solicitudRepository.findAll();
        }else{
            return solicitudRepository.findByNombresContainingIgnoreCase(buscarSolicitud);
        }
    }
    //Guardar
    public Solicitud guardarSolicitud(Solicitud solicitud){
        solicitudRepository.save(solicitud);
        return solicitud;
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
