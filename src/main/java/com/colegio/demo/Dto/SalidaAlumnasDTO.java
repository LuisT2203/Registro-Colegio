package com.colegio.demo.Dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SalidaAlumnasDTO {

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

    public SalidaAlumnasDTO() {
        super();
    }

    public SalidaAlumnasDTO(int id_salida, String nombre_alu, String anio_alu, String nivel, String motivo, String personaquerecoje, String DNI, String telefono, LocalDate fecha, LocalTime hora_salida, LocalTime hora_retorno, int numeroRegistro) {
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
}
