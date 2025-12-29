package com.itsqmet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Encriptador: se va a usar para verificar contraseñas en el login
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Crear cadena de filtros
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/usuarios/formUsuario", "/usuarios/registrarUsuario").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/imagen/**").permitAll()
                        //Proteger rutas o endpoints
                        .requestMatchers("/usuarios/**", "/admin/**").hasRole("ADMIN")
                        .requestMatchers("/solicitud/**").hasAnyRole("EMPLEADO", "ADMIN")
                        .requestMatchers("/permiso/**").hasAnyRole("EMPLEADO", "ADMIN")
                        .requestMatchers("/gastos/**").hasAnyRole( "EMPLEADO", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/postLogin", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Esta es la URL a la que apunta el formulario
                        .logoutSuccessUrl("/login?logout") // A donde va tras cerrar sesión
                        .invalidateHttpSession(true) // Borra la sesión actual
                        .clearAuthentication(true) // Limpia los datos de autenticación
                        .deleteCookies("JSESSIONID") // Borra la cookie de sesión del navegador
                        .permitAll()
                );
        return http.build();
    }

}
