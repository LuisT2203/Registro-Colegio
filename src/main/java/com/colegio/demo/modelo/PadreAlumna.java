package com.colegio.demo.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "padre_alumna")
@IdClass(PadreAlumna.PadreAlumnaId.class)
public class PadreAlumna {
	@Id
	private int id_padre;

	@Id
	private int id_alumna;

	private String parentesco;
	private boolean es_tutor_principal;

	@ManyToOne
	@JoinColumn(name = "id_padre", insertable = false, updatable = false)
	private PadreApoderado padre;

	@ManyToOne
	@JoinColumn(name = "id_alumna", insertable = false, updatable = false)
	private Alumna alumna;

	public PadreAlumna() {
		super();
	}

	public PadreAlumna(int id_padre, int id_alumna, String parentesco, boolean es_tutor_principal) {
		super();
		this.id_padre = id_padre;
		this.id_alumna = id_alumna;
		this.parentesco = parentesco;
		this.es_tutor_principal = es_tutor_principal;
	}

	public int getId_padre() {
		return id_padre;
	}

	public void setId_padre(int id_padre) {
		this.id_padre = id_padre;
	}

	public int getId_alumna() {
		return id_alumna;
	}

	public void setId_alumna(int id_alumna) {
		this.id_alumna = id_alumna;
	}

	public String getParentesco() {
		return parentesco;
	}

	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}

	public boolean isEs_tutor_principal() {
		return es_tutor_principal;
	}

	public void setEs_tutor_principal(boolean es_tutor_principal) {
		this.es_tutor_principal = es_tutor_principal;
	}

	public PadreApoderado getPadre() {
		return padre;
	}

	public void setPadre(PadreApoderado padre) {
		this.padre = padre;
	}

	public Alumna getAlumna() {
		return alumna;
	}

	public void setAlumna(Alumna alumna) {
		this.alumna = alumna;
	}

	public static class PadreAlumnaId implements Serializable {
		private int id_padre;
		private int id_alumna;

		public PadreAlumnaId() {
		}

		public PadreAlumnaId(int id_padre, int id_alumna) {
			this.id_padre = id_padre;
			this.id_alumna = id_alumna;
		}

		public int getId_padre() {
			return id_padre;
		}

		public void setId_padre(int id_padre) {
			this.id_padre = id_padre;
		}

		public int getId_alumna() {
			return id_alumna;
		}

		public void setId_alumna(int id_alumna) {
			this.id_alumna = id_alumna;
		}

		@Override
		public int hashCode() {
			return id_padre + id_alumna;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof PadreAlumnaId) {
				PadreAlumnaId other = (PadreAlumnaId) obj;
				return id_padre == other.id_padre && id_alumna == other.id_alumna;
			}
			return false;
		}
	}
}
