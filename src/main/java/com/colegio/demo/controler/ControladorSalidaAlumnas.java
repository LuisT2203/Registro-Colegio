package com.colegio.demo.controler;

import com.colegio.demo.Dto.EncargoDTO;
import com.colegio.demo.Dto.SalidaAlumnasDTO;
import com.colegio.demo.interfaces.ISalidasAlumnas;
import com.colegio.demo.interfacesService.ISalidaAlumnasService;
import com.colegio.demo.modelo.Encargo;
import com.colegio.demo.modelo.SalidasAlumnas;
import com.colegio.demo.utils.MensajeResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "ControladorSA", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ControladorSalidaAlumnas {
    @Autowired
    private ISalidaAlumnasService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/listarEncargos")
    public ResponseEntity<?> listarSalidas(@RequestParam(name="fechaBusqueda",required=false)String fechaBusqueda,
                                            @RequestParam(name = "encargoNom", required = false) String nombre_alu){
        try{
            List<SalidasAlumnas> salidas;

            if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) {
                LocalDate fecha = LocalDate.parse(fechaBusqueda);
                salidas = listarSalidasPorFecha(fecha);
            } else if (nombre_alu != null) {
                salidas = listarSalidasPorNombre(nombre_alu);
            } else {
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("Debe proporcionarse una fecha o un ID de personal.")
                                .object(null)
                                .build(),
                        HttpStatus.BAD_REQUEST
                );
            }
            // Si no hay resultados
            if (salidas.isEmpty()) {
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("No hay ingresos registrados para los parámetros proporcionados.")
                                .object(null)
                                .build(),
                        HttpStatus.NO_CONTENT
                );
            }
            // Convertir a DTO
            List<SalidaAlumnasDTO> salidasDTO = salidas.stream()
                    .map(salida -> mapper.map(salida, SalidaAlumnasDTO.class))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("Registros encontrados")
                            .object(salidasDTO)
                            .build(),
                    HttpStatus.OK
            );

        } catch (Exception e) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("Error interno del servidor")
                            .object(e.getMessage())
                            .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }

    private List<SalidasAlumnas> listarSalidasPorNombre(String nombre_alu) {
        List<SalidasAlumnas> salidaPorNombre = service.ListarSalidaPorNombre(nombre_alu);
        asignarNumeroDeRegistro(salidaPorNombre);
        return salidaPorNombre;
    }

    private List<SalidasAlumnas> listarSalidasPorFecha(LocalDate fecha){
        List<SalidasAlumnas> salidasPorFecha = service.ListarSalidaPorFecha(fecha);
        asignarNumeroDeRegistro(salidasPorFecha);
        return salidasPorFecha;
    }

    private void asignarNumeroDeRegistro(List<SalidasAlumnas> salidas) {
        for (int i = 0; i < salidas.size(); i++) {
            salidas.get(i).setNumeroRegistro(i + 1);
        }
    }

    @PostMapping(value="/saveSalida", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> guardarE(@Valid @RequestBody SalidaAlumnasDTO bean) {
        try {
            SalidasAlumnas s = mapper.map(bean, SalidasAlumnas.class);
            SalidasAlumnas s1 = service.Guardar(s);
            SalidaAlumnasDTO sadto = mapper.map(s1, SalidaAlumnasDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Se agrego correctamente el ingreso personal")
                    .object(sadto).build(),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(MensajeResponse.builder().
                    mensaje(e.getMessage()).object(null).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value="/updateSalida", consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarE(@Valid @RequestBody Encargo bean) {
        try {
            SalidasAlumnas s = mapper.map(bean, SalidasAlumnas.class);
            SalidasAlumnas s1 = service.Guardar(s);
            SalidaAlumnasDTO sadto = mapper.map(s1, SalidaAlumnasDTO.class);
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("Se actualizó correctamente el ingreso personal")
                    .object(sadto).build(),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(MensajeResponse.builder().
                    mensaje(e.getMessage()).object(null).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/editarSA/{id_salida}")
    public ResponseEntity<?> editarE(@PathVariable ("id_salida") int id_salida) {
        SalidasAlumnas e = service.listarID(id_salida);
        if(e == null) {
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Ingreso Personal no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
        }
        SalidaAlumnasDTO sato = mapper.map(e, SalidaAlumnasDTO.class);
        return new ResponseEntity<>(sato, HttpStatus.OK);
    }

    @DeleteMapping("/eliminarSA/{id_salida}")
    public ResponseEntity<?> eliminarE(@PathVariable ("id_salida") int id_salida) {
        try {
            SalidasAlumnas e = service.listarID(id_salida);
            if (e == null) {
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("ID no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
            }
            service.Borrar(id_salida);
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Eliminado correctamente").object(e).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Error al eliminar").object(null).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
