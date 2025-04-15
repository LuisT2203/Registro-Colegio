package com.colegio.demo.modelo;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "salidas")
public class SalidasAlumnas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_salida;
    private String nombre_alu;
    private String anio_alu;
    private String nivel;
    private String motivo;
    private String personaquerecoje;
    private String DNI;
    private String telefono;
    private LocalDate fecha;
    private LocalTime hora_salida;
    private LocalTime hora_retorno;
    private int numeroRegistro;

    public SalidasAlumnas() {
    }

    public SalidasAlumnas(int id_salida, String nombre_alu, String anio_alu, String nivel, String motivo, String personaquerecoje, String DNI, String telefono, LocalDate fecha, LocalTime hora_salida, LocalTime hora_retorno, int numeroRegistro) {
        this.id_salida = id_salida;
        this.nombre_alu = nombre_alu;
        this.anio_alu = anio_alu;
        this.nivel = nivel;
        this.motivo = motivo;
        this.personaquerecoje = personaquerecoje;
        this.DNI = DNI;
        this.telefono = telefono;
        this.fecha = fecha;
        this.hora_salida = hora_salida;
        this.hora_retorno = hora_retorno;
        this.numeroRegistro = numeroRegistro;
    }

    public int getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(int numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public int getId_salida() {
        return id_salida;
    }

    public void setId_salida(int id_salida) {
        this.id_salida = id_salida;
    }

    public String getNombre_alu() {
        return nombre_alu;
    }

    public void setNombre_alu(String nombre_alu) {
        this.nombre_alu = nombre_alu;
    }

    public String getAnio_alu() {
        return anio_alu;
    }

    public void setAnio_alu(String anio_alu) {
        this.anio_alu = anio_alu;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getPersonaquerecoje() {
        return personaquerecoje;
    }

    public void setPersonaquerecoje(String personaquerecoje) {
        this.personaquerecoje = personaquerecoje;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(LocalTime hora_salida) {
        this.hora_salida = hora_salida;
    }

    public LocalTime getHora_retorno() {
        return hora_retorno;
    }

    public void setHora_retorno(LocalTime hora_retorno) {
        this.hora_retorno = hora_retorno;
    }
}
