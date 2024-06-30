package com.example.demo.controladores;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.exceptions.ServiceException;
import com.example.demo.data.Comision;
import com.example.demo.data.Participante;
import com.example.demo.data.Usuario;
import com.example.demo.servicios.IServicioComision;
import com.example.demo.servicios.IServicioParticipantes;
import com.example.demo.servicios.IServicioUsuario;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/cancelar")
public class CancelarComisionController {

	private final static Logger log = LoggerFactory.getLogger(ListarComisionesController.class);

	@Autowired
	IServicioComision comisionServicio;

	@Autowired
	IServicioParticipantes participanteServicio;

	@Autowired
	IServicioUsuario usuarioServicio;


	@GetMapping(path = "/{id}")
	public String visualizarComision(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes redirectAttributes) throws ServiceException {
		log.info("[GETvisualizarComision]");
		Comision comision = comisionServicio.getComisionById(id).get();
		List<Participante> participantes = participanteServicio.getParticipanteByComisionId(id);
		model.addAttribute("comision", comision);
		model.addAttribute("participantes", participantes);
		return "cancelar";
	}

	@GetMapping(path = "/{id}/comision")
	public String visualizarComisionCancelada(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes redirectAttributes)  throws ServiceException{
		log.info("[GETvisualizarComisionCancelada]");
		Comision comision = comisionServicio.getComisionById(id).get();
		List<Participante> participantes = participanteServicio.getParticipanteByComisionId(id);
		model.addAttribute("comision", comision);
		model.addAttribute("participantes", participantes);
		return "cancelada";
	}

	@PostMapping(path = "/guardarComentario")
	public String guardarComentario(@RequestParam("comisionId") Integer comisionId,
			@RequestParam("comentario") String comentario, RedirectAttributes redirectAttributes, 
			HttpSession session) throws ServiceException {
		log.info("[POSTguardarComentario]");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		// Lógica para guardar el comentario asociado a la comisión con ID comisionId
		comisionServicio.guardarComentario(comisionId, comentario, usuario);
		redirectAttributes.addFlashAttribute("mensaje", "Comentario guardado correctamente");
		return "redirect:/cancelar/" + comisionId;
	}
}
