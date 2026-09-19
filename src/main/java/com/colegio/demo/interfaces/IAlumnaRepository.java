package com.colegio.demo.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colegio.demo.modelo.Alumna;

@Repository
public interface IAlumnaRepository extends JpaRepository<Alumna, Integer> {

	Page<Alumna> findByGradoAndNivelAndSeccion(String grado, String nivel, String seccion, Pageable pageable);
}
