package com.example.demo.controladores;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Importa el paquete correcto
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.data.Comision;
import com.example.demo.data.Participante;
import com.example.demo.data.Usuario;
import com.example.demo.servicios.IServicioComision;
import com.example.demo.servicios.IServicioParticipantes;
import com.example.demo.servicios.IServicioUsuario;

@Controller
@RequestMapping(path = "/crear")
public class CrearComisionControlador {

	private final static Logger log = LoggerFactory.getLogger(LoginControlador.class);

	@Autowired
	IServicioComision comisionServicio;

	@Autowired
	IServicioUsuario usuarioServicio;

	@Autowired
	IServicioParticipantes participanteServicio;

	@GetMapping("/comision")
	public String mostrarFormulario(Model model) {
		log.info("[GETmostrarFormulario]");
		model.addAttribute("listar_participantes", participanteServicio.getAllParticipantes());
		model.addAttribute("comision", new Comision());
		return "crear";
	}

	@PostMapping("/guardar")
	public String guardarComision(@ModelAttribute("comision") Comision comision, BindingResult bindingResult,
			Model model, @RequestParam("selectedParticipants") String selectedParticipants,
			Authentication authentication) {
		log.info("[POSTguardarComision]");
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new IllegalStateException("Controlador No se encontró un usuario autenticado.");
		}

		// Obtener el nombre de usuario del usuario autenticado
		String username = authentication.getName();
		log.info("Usuario que está creando la comisión: " + username);
		// Obtener el objeto Usuario desde el servicio de usuarios
		Usuario usuarioCreador = usuarioServicio.getUsuario(username);

		log.info("[USUARIO CREADOR]" + comision.getUsuarioCreador());

		// Obtener los IDs de los participantes seleccionados y convertirlos a Long
		List<Integer> participantesIds = Arrays.stream(selectedParticipants.split(","))
				.filter(id -> !id.trim().isEmpty()) // Filtrar elementos vacíos
				.map(Integer::valueOf).collect(Collectors.toList());

		log.info("IDs de participantes seleccionados: " + participantesIds);

		// Obtener los objetos de participantes seleccionados del servicio de
		// participantes usando sus IDs
		List<Participante> participantesSeleccionados = participanteServicio.getParticipantesByIds(participantesIds);

		log.info("Participantes seleccionados: " + participantesSeleccionados);

		// Establecer los participantes seleccionados en el objeto Comision
		comision.setParticipantes(participantesSeleccionados);

		// Guardar la comisión con los participantes seleccionados
		comisionServicio.saveComision(comision, usuarioCreador);

		log.info("Comisión creada y guardada correctamente");

		return "redirect:/listar";
	}

}
