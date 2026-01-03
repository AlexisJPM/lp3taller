package com.itsqmet.controller;

import com.itsqmet.model.Permiso;
import com.itsqmet.model.Usuario;
import com.itsqmet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String panelAdmin(Model model){
        model.addAttribute("permiso", new Permiso());
        return  "pages/permisoForm";
    }
    // 1. Pantalla para ver todos los usuarios
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", userService.listarTodos());
        return "pages/listUserGastos";
    }

    // 2. Pantalla detallada de gastos Usando LAZY
    @GetMapping("/usuario/{id}/gastos")
    public String detalleGastos(@PathVariable Long id, Model model) {
        Usuario usuario = userService.obtenerUsuarioConGastosLazy(id);
        model.addAttribute("usuario", usuario);
        return "pages/detallesGastos";
    }
}
