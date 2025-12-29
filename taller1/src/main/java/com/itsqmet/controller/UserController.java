package com.itsqmet.controller;

import com.itsqmet.model.Usuario;
import com.itsqmet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService
            userService;
    //LEER LA INFORMACION
    @GetMapping
    public String mostrarUsuarios(Model model){
        List<Usuario> usuarios = userService.mostrarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "pages/listUser";
    }

    //GUARDAR
    //1.Mostrar formulario
    @GetMapping("/formUsuario")
    public String formularioUsuario(Model model){
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "pages/userForm";
    }
    //2. Enviar los datos ingresados en el form a la db
    @PostMapping("/registrarUsuario")
    public String guardarUsuario(Usuario usuario) {
        if (usuario.getId() != null){
            userService.actualizarUsuario(usuario.getId(), usuario);
        }else{
            userService.guardarUsuario(usuario);
        }
        return "redirect:/usuarios";
    }

    //Actualizar
    @GetMapping("/editarUsuario/{id}")
    public String  actualizarUsuario(@PathVariable Long id, Model model){
        Optional<Usuario> usuario = userService.buscarUsuarioById(id);
        model.addAttribute("usuario", usuario);
        return "pages/userForm";
    }

    //Eliminar
    @DeleteMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable Long id){
        userService.eliminarUsuario(id);
        return "redirect:/usuarios";
    }

}
