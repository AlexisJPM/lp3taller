package com.itsqmet.controller;

import com.itsqmet.model.Permiso;
import com.itsqmet.model.Solicitud;
import com.itsqmet.model.Usuario;
import com.itsqmet.repository.UserRepository;
import com.itsqmet.service.SolicitudService;
import com.itsqmet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private UserRepository userRepository;

    // 1. LISTAR SOLICITUDES (Solo las del usuario logueado)
    @GetMapping
    public String listarSolicitudes(Model model, Authentication authentication) {
        String username = authentication.getName();
        Usuario usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Solicitud> lista  = solicitudService.mostrarSolicitudesPorUsuario(usuario.getId());
        model.addAttribute("solicitud", lista);
        return "pages/solicitudList";
    }

    // 2. MOSTRAR FORMULARIO (Para crear nueva)
    @GetMapping("/form")
    public String formSolicitud(Model model) {
        model.addAttribute("solicitud", new Solicitud());
        return "pages/solicitudForm";
    }

    // 3. GUARDAR O ACTUALIZAR
    @PostMapping("/registrarSolicitud")
    public String registrar(@Valid @ModelAttribute Solicitud solicitud, Authentication authentication) {
        String username = authentication.getName();
        Usuario usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        solicitud.setUsuario(usuario); // Asignamos el dueño de la solicitud
        if (solicitud.getId() != null) {
            solicitudService.actualizarSolicitud(solicitud.getId(), solicitud);
        } else {
            solicitudService.guardarSolicitud(solicitud);
        }
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
