package com.colegio.demo.interfaces;



import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;


import com.colegio.demo.modelo.PersonalColegio;
@Repository
public interface IPersonalColegio extends CrudRepository<PersonalColegio, Integer> {
	 
}
