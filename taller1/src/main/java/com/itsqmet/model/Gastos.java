package com.itsqmet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Entity
@Table(name = "gastos")
public class Gastos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer periodo;

    @PositiveOrZero
    private BigDecimal vivienda;

    @PositiveOrZero
    private BigDecimal educacion;

    @PositiveOrZero
    private BigDecimal salud;

    @PositiveOrZero
    private BigDecimal vestimenta;

    @PositiveOrZero
    private BigDecimal alimentacion;

    @PositiveOrZero
    private BigDecimal turismo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Gastos() {
    }

    public Gastos(Long id, Integer periodo, BigDecimal vivienda, BigDecimal educacion, BigDecimal salud, BigDecimal vestimenta, BigDecimal alimentacion, BigDecimal turismo, Usuario usuario) {
        this.id = id;
        this.periodo = periodo;
        this.vivienda = vivienda;
        this.educacion = educacion;
        this.salud = salud;
        this.vestimenta = vestimenta;
        this.alimentacion = alimentacion;
        this.turismo = turismo;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public BigDecimal getVivienda() {
        return vivienda;
    }

    public void setVivienda(BigDecimal vivienda) {
        this.vivienda = vivienda;
    }

    public BigDecimal getEducacion() {
        return educacion;
    }

    public void setEducacion(BigDecimal educacion) {
        this.educacion = educacion;
    }

    public BigDecimal getSalud() {
        return salud;
    }

    public void setSalud(BigDecimal salud) {
        this.salud = salud;
    }

    public BigDecimal getVestimenta() {
        return vestimenta;
    }

    public void setVestimenta(BigDecimal vestimenta) {
        this.vestimenta = vestimenta;
    }

    public BigDecimal getAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(BigDecimal alimentacion) {
        this.alimentacion = alimentacion;
    }

    public BigDecimal getTurismo() {
        return turismo;
    }

    public void setTurismo(BigDecimal turismo) {
        this.turismo = turismo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
