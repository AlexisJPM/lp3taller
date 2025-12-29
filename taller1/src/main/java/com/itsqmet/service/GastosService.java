package com.itsqmet.service;

import com.itsqmet.model.Gastos;
import com.itsqmet.model.Permiso;
import com.itsqmet.repository.GastosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class GastosService {

    @Autowired
    private GastosRepository gastosRepository;

    public List<Gastos> mostrarGasto(){
        return gastosRepository.findAll();
    }

    //Buscar por id
    public Optional<Gastos> buscarGastosById (Long id){
        return gastosRepository.findById(id);
    }

    //Guardar
    public Gastos guardarGasto(Gastos gastos){
        gastosRepository.save(gastos);
        return gastos;
    }

    //Actualizar
    public Gastos actualizarGastos(Long id, Gastos gastos){
        Gastos gastoExiste = buscarGastosById(id)
                .orElseThrow(()-> new RuntimeException("Gastos no existe"));
        gastoExiste.setPeriodo(gastos.getPeriodo());
        gastoExiste.setVivienda(gastos.getVivienda());
        gastoExiste.setEducacion(gastos.getEducacion());
        gastoExiste.setSalud(gastos.getSalud());
        gastoExiste.setVestimenta(gastos.getVestimenta());
        gastoExiste.setAlimentacion(gastos.getAlimentacion());
        gastoExiste.setTurismo(gastos.getTurismo());
        return gastosRepository.save(gastoExiste);
    }

    //Eliminar
    public void eliminarGasto(Long id){
        Gastos gastos = buscarGastosById(id)
                //se lanza cuando no encuentra el producto
                .orElseThrow(() -> new ResponseStatusException(
                        //constante de spring que representa el codigo http 404
                        //404=recurso no encontrado
                        HttpStatus.NOT_FOUND, "Gastos no existen"
                ));
        gastosRepository.delete(gastos);
    }

}
