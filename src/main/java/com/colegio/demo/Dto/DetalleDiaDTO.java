package com.colegio.demo.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class DetalleDiaDTO {
    private int id;
    private LocalDate fecha;
    private LocalTime horaIngreso;
    private LocalTime horaSalida;
    private double horas;
    private boolean estimado;

    public DetalleDiaDTO() {}

    public DetalleDiaDTO(int id, LocalDate fecha, LocalTime horaIngreso, LocalTime horaSalida, double horas, boolean estimado) {
        this.id = id;
        this.fecha = fecha;
        this.horaIngreso = horaIngreso;
        this.horaSalida = horaSalida;
        this.horas = horas;
        this.estimado = estimado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHoraIngreso() { return horaIngreso; }
    public void setHoraIngreso(LocalTime horaIngreso) { this.horaIngreso = horaIngreso; }
    public LocalTime getHoraSalida() { return horaSalida; }
    public void setHoraSalida(LocalTime horaSalida) { this.horaSalida = horaSalida; }
    public double getHoras() { return horas; }
    public void setHoras(double horas) { this.horas = horas; }
    public boolean isEstimado() { return estimado; }
    public void setEstimado(boolean estimado) { this.estimado = estimado; }
}
