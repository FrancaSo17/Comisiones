package com.example.demo.data;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="historico")
public class Historico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int IdComision;

	private String EstadoInicial;

	private String EstadoFinal;

	// fecha

	
	@OneToMany
	@JoinColumn(name="USUARIOS_HISTORICO")
	private List<Usuario> usuario;
	
	//CONSTRUCTOR
	
	public Historico() {
		super();
	}


	private String motivo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdComision() {
		return IdComision;
	}

	public void setIdComision(int idComision) {
		IdComision = idComision;
	}

	public String getEstadoInicial() {
		return EstadoInicial;
	}

	public void setEstadoInicial(String estadoInicial) {
		EstadoInicial = estadoInicial;
	}

	public String getEstadoFinal() {
		return EstadoFinal;
	}

	public void setEstadoFinal(String estadoFinal) {
		EstadoFinal = estadoFinal;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	
}
