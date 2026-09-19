package com.colegio.demo.Dto;

import java.util.List;

public class InformePersonalDTO {
	private PersonaDTO personal;
	private double totalHoras;
	private int diasTrabajados;
	private double promedioDiario;
	private List<DetalleDiaDTO> detalle;

	public InformePersonalDTO() {
	}

	public InformePersonalDTO(PersonaDTO personal, double totalHoras, int diasTrabajados, double promedioDiario,
			List<DetalleDiaDTO> detalle) {
		this.personal = personal;
		this.totalHoras = totalHoras;
		this.diasTrabajados = diasTrabajados;
		this.promedioDiario = promedioDiario;
		this.detalle = detalle;
	}

	public PersonaDTO getPersonal() {
		return personal;
	}

	public void setPersonal(PersonaDTO personal) {
		this.personal = personal;
	}

	public double getTotalHoras() {
		return totalHoras;
	}

	public void setTotalHoras(double totalHoras) {
		this.totalHoras = totalHoras;
	}

	public int getDiasTrabajados() {
		return diasTrabajados;
	}

	public void setDiasTrabajados(int diasTrabajados) {
		this.diasTrabajados = diasTrabajados;
	}

	public double getPromedioDiario() {
		return promedioDiario;
	}

	public void setPromedioDiario(double promedioDiario) {
		this.promedioDiario = promedioDiario;
	}

	public List<DetalleDiaDTO> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<DetalleDiaDTO> detalle) {
		this.detalle = detalle;
	}
}
