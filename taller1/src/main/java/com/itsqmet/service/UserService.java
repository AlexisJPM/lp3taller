package com.itsqmet.service;

import com.itsqmet.model.Usuario;
import com.itsqmet.repository.UserRepository;
import com.itsqmet.roles.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    //LEER
    public List<Usuario> mostrarUsuarios(){
        return userRepository.findAll();
    }

    //Buscar por id
    public Optional<Usuario> buscarUsuarioById (Long id){
        return userRepository.findById(id);
    }

    //Guardar
    public Usuario guardarUsuario(Usuario usuario){
        //encriptar la contraseña antes de guardar
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);
        //asignar el rol de estudiante por default a todos los usuarios
        usuario.setRol(Rol.ROLE_EMPLEADO);
        return userRepository.save(usuario);
    }

    //Actualizar
    public Usuario actualizarUsuario(Long id, Usuario usuario){
        Usuario usuarioExistente = buscarUsuarioById(id)
                .orElseThrow(()-> new RuntimeException("Usuario no existe"));
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setUsername(usuario.getUsername());
        usuarioExistente.setEmail(usuario.getEmail());
        //Actualizacion del password solo si el usuario lo modifica
        if(usuario.getPassword() != null && !usuario.getPassword().trim().isEmpty()){
            usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return userRepository.save(usuarioExistente);
    }

    //Eliminar
    public void eliminarUsuario(Long id){
        Usuario usuario = buscarUsuarioById(id)
                //se lanza cuando no encuentra el producto
                .orElseThrow(() -> new ResponseStatusException(
                        //constante de spring que representa el codigo http 404
                        //404=recurso no encontrado
                        HttpStatus.NOT_FOUND, "Usuario no existe"
                ));
        userRepository.delete(usuario);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Buscra en la db al usuario que esta autenticandose, si no existe se lanza uan exception
        //para denegar el acceso
        Usuario usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        //Usar el metodo builder para contruir el objeto que se entiende como usuario autenticado
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(usuario.getRol().name())
                .build();
    }

}
