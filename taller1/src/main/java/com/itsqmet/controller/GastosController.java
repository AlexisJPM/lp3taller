package com.itsqmet.controller;

import com.itsqmet.model.Gastos;
import com.itsqmet.service.GastosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gastos"
)
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

}
