package com.example.demo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.Comision;

public interface ComisionRepositorio extends JpaRepository<Comision, Integer> {
	
	
	List<Comision> findAllByOrderByFechaComisionAscPrioridadAsc();
	
	

}
