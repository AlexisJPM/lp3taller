package com.itsqmet.controller;

import com.itsqmet.model.Permiso;
import com.itsqmet.service.PermisoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/permiso")
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    //LEER LA INFORMACION
    @GetMapping
    public String mostrarPermisos(Model model){
        List<Permiso> listaPermisos = permisoService.mostrarPermiso();
        // Asegúrate de que este nombre sea el que uses en el th:each
        model.addAttribute("listaPermisos", listaPermisos);
        return "pages/solicitudList";
    }

    // 1. Mostrar formulario de permiso
    @GetMapping("/formPermiso")
    public String formPermisos(Model model) {
        Permiso permiso = new Permiso();
        model.addAttribute("permiso", permiso);
        return "pages/permisoForm";
    }

    // 2. Enviar los datos ingresados en el form a la db
    @PostMapping("/registrarPermiso")
    public String guardarPermiso(@Valid @ModelAttribute Permiso permiso, BindingResult result) {
        if (result.hasErrors()) {
            return "pages/permisoForm"; // Regresa al form si hay errores de validación
        }
        permisoService.guardarPermiso(permiso);
        return "redirect:/permiso"; // Redirige a la lista principal
    }


}
