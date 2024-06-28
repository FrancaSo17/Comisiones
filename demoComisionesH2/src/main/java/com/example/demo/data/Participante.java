package com.example.demo.data;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Participante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String nombre;

	private String apellidos;

	private String email;

	@ManyToMany(mappedBy = "participantes")
	private List<Comision> comision;

	public List<Comision> getComisiones() {
		return comision;
	}

	public void setComisiones(List<Comision> comisiones) {
		this.comision = comisiones;
	}

	public Participante() {
		super();
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	// GETTERS && SETTERS

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
