package com.itsqmet.controller;

import com.itsqmet.model.Permiso;
import com.itsqmet.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public String panelAdmin(Model model){
        model.addAttribute("permiso", new Permiso());
        return  "pages/permisoForm";
    }
}
