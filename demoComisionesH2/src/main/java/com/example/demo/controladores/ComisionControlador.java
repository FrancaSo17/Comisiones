package com.example.demo.controladores;

import java.util.List;
import java.util.Optional;

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

	@Autowired
	private ComisionServicio comisionServicio;
	
	@GetMapping
	public List<Comision>	getAllComisiones(){
		return comisionServicio.getAllComsiones();
	}
	
	 @GetMapping("/{id}")
	 public Optional<Comision> getComisionById(@PathVariable("id") Integer id){
		 return comisionServicio.getComisionById(id);
	 }
	 
//	 @PostMapping
//	 public Comision createComision(@RequestBody Comision comision) {
//		 return comisionServicio.saveComision(comision);
//	 }
	 
	 @DeleteMapping("/{id}")
	 public void deleteComision(@PathVariable("id") Integer id) {
		 comisionServicio.deleteComision(id);
	 }
	
}
