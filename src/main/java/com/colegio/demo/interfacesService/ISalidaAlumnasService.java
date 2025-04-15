package com.colegio.demo.interfacesService;

import com.colegio.demo.modelo.Encargo;
import com.colegio.demo.modelo.SalidasAlumnas;

import java.time.LocalDate;
import java.util.List;

public interface ISalidaAlumnasService {
    List<SalidasAlumnas> ListarSalidasAlumnas();
    SalidasAlumnas listarID(int id_salida);
    SalidasAlumnas Guardar (SalidasAlumnas sa);
    SalidasAlumnas Borrar(int id_salida);
    List<SalidasAlumnas> ListarSalidaPorFecha (LocalDate fecha);
    List<SalidasAlumnas> ListarSalidaPorNombre(String nombre_alu);
}
