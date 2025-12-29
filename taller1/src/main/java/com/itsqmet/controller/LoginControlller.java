package com.itsqmet.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginControlller {

    @GetMapping("/login")
    public String login(){
        return "pages/login";
    }
    //Indicar a donde se dirige un ususario despues de la autenticacion
    @GetMapping("/postLogin")
    public String dirigirPorRol( Authentication authentication){
        User usuario = (User)  authentication.getPrincipal();
        String role = usuario.getAuthorities().stream()
                //extrae el nombre del rol (ROLE_ADMIN) de cada objeto de autoridad
                .map(granteAuthority -> granteAuthority.getAuthority())
                //toma el primer rol que encuentra en la lista
                .findFirst()
                //si el usuaruio no tiene ningun rol devuelve vacio
                .orElse("");
        if(role.equals("ROLE_ADMIN")){
            return "redirect:/admin";
        } else if (role.equals("ROLE_EMPLEADO")) {
            return "redirect:/permiso";
        }
        //si el rol no coincide con ninguno
        return "redirect:/login?error";
    }

}
