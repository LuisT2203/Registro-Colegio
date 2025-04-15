package com.colegio.demo.service;

import com.colegio.demo.interfaces.ISalidasAlumnas;
import com.colegio.demo.interfacesService.ISalidaAlumnasService;
import com.colegio.demo.modelo.SalidasAlumnas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class SalidaAlumnasService implements ISalidaAlumnasService {

    @Autowired
    private ISalidasAlumnas repo;

    @Override
    public List<SalidasAlumnas> ListarSalidasAlumnas() {
            return (List<SalidasAlumnas>) repo.findAll();
    }

    @Override
    public SalidasAlumnas listarID(int id_salida) {
        return repo.findById(id_salida).orElse(new SalidasAlumnas());
    }

    @Override
    public SalidasAlumnas Guardar(SalidasAlumnas sa) {
        SalidasAlumnas salida = repo.save(sa);
        return salida;
    }

    @Override
    public SalidasAlumnas Borrar(int id_salida) {
        SalidasAlumnas temp = repo.findById(id_salida).orElse(null);
        if (temp == null) {
            return new SalidasAlumnas();
        }else {
            repo.deleteById(id_salida);
            return temp;
        }
    }

    @Override
    public List<SalidasAlumnas> ListarSalidaPorFecha(LocalDate fecha) {
        return repo.listarSalidasPorFecha(fecha);
    }

    @Override
    public List<SalidasAlumnas> ListarSalidaPorNombre(String nombre_alu) {
        return repo.BuscarAlumnaNombre(nombre_alu);
    }
}
