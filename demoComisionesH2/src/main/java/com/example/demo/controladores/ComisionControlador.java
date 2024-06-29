package com.example.demo.controladores;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.Comision;
import com.example.demo.servicios.ComisionServicio;

@RestController
@RequestMapping("/comisiones")
public class ComisionControlador {
	
	private final static Logger log = LoggerFactory.getLogger(ListarComisionesController.class);

	@Autowired
	private ComisionServicio comisionServicio;
	
	@GetMapping
	public List<Comision>	getAllComisiones(){
		log.info("[GETgetAllComisiones]");
		return comisionServicio.getAllComsiones();
	}
	
	 @GetMapping("/{id}")
	 public Optional<Comision> getComisionById(@PathVariable("id") Integer id){
		 log.info("[GETgetComisionById]");
		 return comisionServicio.getComisionById(id);
	 }
	 
	 @DeleteMapping("/{id}")
	 public void deleteComision(@PathVariable("id") Integer id) {
		 log.info("[DELETEdeleteComision]");
		 comisionServicio.deleteComision(id);
	 }
	
}
