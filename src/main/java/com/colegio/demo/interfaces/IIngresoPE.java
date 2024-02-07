package com.colegio.demo.interfaces;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.colegio.demo.modelo.IngresoPersonaExterna;
@Repository
public interface IIngresoPE extends CrudRepository<IngresoPersonaExterna, Integer> {

}
