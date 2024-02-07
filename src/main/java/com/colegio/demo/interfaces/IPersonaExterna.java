package com.colegio.demo.interfaces;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.colegio.demo.modelo.PersonaExterna;
@Repository
public interface IPersonaExterna extends CrudRepository<PersonaExterna, Integer> {

}
