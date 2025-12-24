package com.itsqmet.controller;

import com.itsqmet.model.Permiso;
import com.itsqmet.model.Solicitud;
import com.itsqmet.service.SolicitudService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/solicitud")
public class FormController {
    @Autowired
    private SolicitudService solicitudService;

    //LEER LA INFORMACION
    @GetMapping
    public String mostrarSolicitud(@RequestParam(name = "buscarSolicitud",
            required = false, defaultValue = "") String buscarSolicitud, Model model) {
        List<Solicitud> solicitud = solicitudService.buscarSolicitudPorNombre(buscarSolicitud);
        model.addAttribute("buscarSolicitud", buscarSolicitud);
        model.addAttribute("solicitud", solicitud);
        return "pages/solicitudList";
    }

    //GUARDAR
    //1.Mostrar formulario
    @GetMapping("/form")
    public String formSolicitud(Model model) {
        Solicitud solicitud = new Solicitud();
        model.addAttribute("solicitud", solicitud);
        return "pages/solicitudForm";
    }

    //2. Enviar los datos ingresados en el form a la db
    @PostMapping("/registrarSolicitud")
    public String guardarSolicitud(@Valid @ModelAttribute Solicitud solicitud) {
        solicitudService.guardarSolicitud(solicitud);
        return "redirect:/solicitud";
    }

    //Actualizar
    @GetMapping("/editarSolicitud/{id}")
    public String actualizarSolicitud(@PathVariable Long id, Model model) {
        Optional<Solicitud> solicitud = solicitudService.buscarSolicitudById(id);
        model.addAttribute("solicitud", solicitud);
        return "pages/solicitudForm";
    }

    //Eliminar libro
    @DeleteMapping("/eliminarSolicitud/{id}")
    public String eliminarSolicictud(@PathVariable Long id) {
        solicitudService.eliminarSolicitud(id);
        return "redirect:/solicitud";
    }


}
