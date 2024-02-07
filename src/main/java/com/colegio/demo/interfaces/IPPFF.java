package com.colegio.demo.interfaces;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.colegio.demo.modelo.PPFF;
@Repository
public interface IPPFF extends CrudRepository<PPFF, Integer> {

}
