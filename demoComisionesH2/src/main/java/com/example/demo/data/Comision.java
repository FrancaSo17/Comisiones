package com.example.demo.data;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comision")
public class Comision {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private boolean verificada;

	private boolean validada;
	
	private boolean cancelada;
	
	private boolean aprobada;
	
	private String motivoCancelacion;

	private String titulo;

	@Column(name = "FECHACOMISION")
	private LocalDate fechaComision;

	@Column(name = "FECHAVERIFICACION")
	private LocalDate fechaVerificacion;

	
	@Column(name = "FECHAVALIDACION")
	private LocalDate fechaValidacion;

	
    private String estado;

	private String descripcion;
	
	
	private int prioridad;

	@OneToOne
	@JoinColumn(name = "USUARIO_CREADOR_ID")
	private Usuario usuarioCreador;

	@OneToOne
	@JoinColumn(name = "USUARIO_VALIDADOR_ID")
	private Usuario usuarioValidador;

	@OneToOne
	@JoinColumn(name = "USUARIO_VERIFICADOR_ID")
	private Usuario usuarioVerificador;

	@OneToOne
	@JoinColumn(name = "USUARIO_CANCELADOR_ID")
	private Usuario usuarioCancelador;
	
	@OneToMany(mappedBy = "comision", cascade= CascadeType.ALL, orphanRemoval= true)
	private List<Documento> documentos;
	
	public Usuario getUsuarioCancelador() {
		return usuarioCancelador;
	}

	public void setUsuarioCancelador(Usuario usuarioCancelador) {
		this.usuarioCancelador = usuarioCancelador;
	}

	@ManyToMany
    @JoinTable(
        name = "comision_participante",
        joinColumns = @JoinColumn(name = "comision_id"),
        inverseJoinColumns = @JoinColumn(name = "participante_id")
    )
    private List<Participante> participantes;
	
	

	public List<Participante> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Participante> participantes) {
		
		this.participantes = participantes;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDate getFechaVerificacion() {
		return fechaVerificacion;
	}

	public void setFechaVerificacion(LocalDate fechaVerificacion) {
		this.fechaVerificacion = fechaVerificacion;
	}

	public LocalDate getFechaValidacion() {
		return fechaValidacion;
	}

	public void setFechaValidacion(LocalDate fechaValidacion) {
		this.fechaValidacion = fechaValidacion;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// Constructor
	public Comision() {
		super();
	}

	// GETTERS && SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	

	public int getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}

	public Usuario getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(Usuario usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public Usuario getUsuarioValidador() {
		return usuarioValidador;
	}

	public void setUsuarioValidador(Usuario usuarioValidador) {
		this.usuarioValidador = usuarioValidador;
	}

	public Usuario getUsuarioVerificador() {
		return usuarioVerificador;
	}

	public void setUsuarioVerificador(Usuario usuarioVerificador) {
		this.usuarioVerificador = usuarioVerificador;
	}

	public boolean isVerificada() {
		return verificada;
	}

	public void setVerificada(boolean verificada) {
		this.verificada = verificada;
	}

	public boolean isValidada() {
		return validada;
	}

	public Optional<Comision> setValidada(boolean validada) {
		this.validada = validada;

		return null;
	}

	public LocalDate getFechaComision() {
		return fechaComision;
	}

	public void setFechaComision(LocalDate fechaComision) {
		this.fechaComision = fechaComision;
	}

	@Override
	public String toString() {
		return "Comision [id=" + id + ", verificada=" + verificada + ", validada=" + validada + ", titulo=" + titulo
				+ ", fechaComision=" + fechaComision + ", fechaVerificacion=" + fechaVerificacion + ", fechaValidacion="
				+ fechaValidacion + ", estado=" + estado + ", prioridad=" + prioridad + ", usuarioCreador="
				+ usuarioCreador + ", usuarioCancelador=" + usuarioCancelador + ", usuarioValidador=" + usuarioValidador
				+ ", usuarioVerificador=" + usuarioVerificador + "]";
	}

	public boolean isCancelada() {
		return cancelada;
	}

	public void setCancelada(boolean cancelada) {
		this.cancelada = cancelada;
	}

	public String getMotivoCancelacion() {
		return motivoCancelacion;
	}

	public void setMotivoCancelacion(String motivoCancelacion) {
		this.motivoCancelacion = motivoCancelacion;
	}

	public boolean isAprobada() {
		return aprobada;
	}

	public void setAprobada(boolean aprobada) {
		this.aprobada = aprobada;
	}

}
