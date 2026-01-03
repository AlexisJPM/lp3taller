package com.itsqmet.model;

import com.itsqmet.roles.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String nombre;

    @Column(unique = true)
    @Email
    private String email;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Gastos> listaGastos;

    @OneToMany(mappedBy = "usuario")
    private List<Permiso> listaPermisos;

    @OneToMany(mappedBy = "usuario")
    private List<Solicitud> listaSolicitudes;

    public Usuario() {
    }

    public Usuario(Long id, String nombre, String email, String username, String password, Rol rol, List<Gastos> listaGastos, List<Permiso> listaPermisos, List<Solicitud> listaSolicitudes) {
        Id = id;
        this.nombre = nombre;
        this.email = email;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.listaGastos = listaGastos;
        this.listaPermisos = listaPermisos;
        this.listaSolicitudes = listaSolicitudes;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Gastos> getListaGastos() {
        return listaGastos;
    }

    public void setListaGastos(List<Gastos> listaGastos) {
        this.listaGastos = listaGastos;
    }

    public List<Permiso> getListaPermisos() {
        return listaPermisos;
    }

    public void setListaPermisos(List<Permiso> listaPermisos) {
        this.listaPermisos = listaPermisos;
    }

    public List<Solicitud> getListaSolicitudes() {
        return listaSolicitudes;
    }

    public void setListaSolicitudes(List<Solicitud> listaSolicitudes) {
        this.listaSolicitudes = listaSolicitudes;
    }
}
