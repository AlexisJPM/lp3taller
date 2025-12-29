package com.itsqmet.controller;

import com.itsqmet.model.Gastos;
import com.itsqmet.model.Permiso;
import com.itsqmet.service.GastosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/gastos")
public class GastosController {

    @Autowired
    private GastosService gastosService;

    @GetMapping("/nuevoGasto")
    public String formGasto(Model model) {
        // Para el formulario: enviamos un objeto vacío
        model.addAttribute("nuevoGasto", new Gastos());

        // Para la tabla: enviamos la lista de la base de datos
        model.addAttribute("listaDeGastos", gastosService.mostrarGasto());

        return "pages/gastosForm";
    }

    @PostMapping("/registrarGasto")
    public String guardar(@ModelAttribute("nuevoGasto") Gastos gastos) {
        gastosService.guardarGasto(gastos);
        return "redirect:/gastos/nuevoGasto"; // Redirige para refrescar la tabla
    }

    //Actualizar
    @GetMapping("/editarGasto/{id}")
    public String actualizarGastos(@PathVariable Long id, Model model) {
        Optional<Gastos> gastos = gastosService.buscarGastosById(id);
        model.addAttribute("nuevoGasto", gastos.orElse(new Gastos()));
        model.addAttribute("listaDeGastos", gastosService.mostrarGasto());
        return "pages/gastosForm";
    }

    //Eliminar libro
    @DeleteMapping("/eliminarGasto/{id}")
    public String eliminarGastos(@PathVariable Long id) {
        gastosService.eliminarGasto(id);
        return "redirect:/gastos/nuevoGasto";
    }

}
