package com.itsqmet.service;

import com.itsqmet.model.Gastos;
import com.itsqmet.model.Permiso;
import com.itsqmet.repository.GastosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
