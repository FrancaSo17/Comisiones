package com.example.demo.controladores;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class ListarComisionesController {

	private final static Logger log = LoggerFactory.getLogger(ListarComisionesController.class);

	@Autowired
	IServicioComision comisionServicio;

	@Autowired
	IServicioParticipantes participanteServicio;

	@Autowired
	IServicioUsuario usuarioServicio;

	@GetMapping(value = { "/listar" })
	public String listar(Model model, HttpSession session) throws ServiceException{
		log.info("[GETlistar]");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		List<Comision> comisiones = comisionServicio.getAllComsiones();

		List<Comision> comisionesSinVerificar = comisionServicio.getAllcomisionesSinVerificar(usuario);

		List<Comision> comisionesSinValidar = comisionServicio.getAllcomisionesSinValidar(usuario);

		List<Comision> comisionesCanceladas = comisionServicio.getAllcomisionesCanceladas();

		List<Comision> comisionesAprobadas = comisionServicio.getAllcomisionesAprobadas();

		model.addAttribute("comisionesSinVerificar", comisionesSinVerificar);

		model.addAttribute("comisionesSinValidar", comisionesSinValidar);

		model.addAttribute("comisionesCanceladas", comisionesCanceladas);

		model.addAttribute("comisionesAprobadas", comisionesAprobadas);

		model.addAttribute("listar", comisiones);

		return "listar";
	}

	@GetMapping(path = "/visualizar/{id}")
	public String visualizarComision(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes redirectAttributes) throws ServiceException{
		log.info("[GETvisualizarComision]");
		Comision comision = comisionServicio.getComisionById(id).get();
		List<Participante> participantes = participanteServicio.getParticipanteByComisionId(id);
		model.addAttribute("comision", comision);
		model.addAttribute("participantes", participantes);
		return "visualizar";
	}

	@GetMapping(path = "/eliminar/{id}")
	public String borrarComision(@PathVariable(name = "id") Integer id) throws ServiceException {
		log.info("[GETborrarComision]");
		comisionServicio.deleteComision(id);
		return "redirect:/listar";
	}

	@GetMapping(path = "/datosComision/{id}")
	public String mostrarComision(@PathVariable(name = "id") Integer id) throws ServiceException{
		log.info("[GETmostrarComision]");
		comisionServicio.getComisionById(id);
		return "redirect:/visualizar";
	}

	@GetMapping(path = "/validarComision/{id}")
	public String validarComision(@PathVariable(name = "id") Integer id, HttpSession session) throws ServiceException{
		log.info("[GETvalidarComision]");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		comisionServicio.validarComision(id, usuario);
		return "redirect:/listar";
	}

	@GetMapping(path = "/verificarComision/{id}")
	public String verificarComision(@PathVariable(name = "id") Integer id, HttpSession session) throws ServiceException {
		log.info("[GETverificarComision]");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		comisionServicio.verificarComision(id, usuario);
		return "redirect:/listar";
	}

	@GetMapping(path = "/noValidarComision/{id}")
	public String noValidarComision(@PathVariable(name = "id") Integer id, HttpSession session) throws ServiceException {
		log.info("[GETnoValidarComision]");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		comisionServicio.noValidarComision(id, usuario);
		return "redirect:/cancelar/{id}";
	}

	@GetMapping(path = "/noVerificarComision/{id}")
	public String noVerificarComision(@PathVariable(name = "id") Integer id, HttpSession session) throws ServiceException{
		log.info("[GETnoVerificarComision]");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		comisionServicio.noVerificarComision(id, usuario);
		return "redirect:/noVerificar/{id}";
	}

	//No se utiliza
	@GetMapping(path = "/eliminarParticipante/{comisionId}/{id}")
	public String borrarParticipante(@PathVariable(name = "comisionId") Integer comisionId,
			@PathVariable(name = "id") Integer id) throws ServiceException{
		log.info("[GETborrarParticipante]");
		participanteServicio.deleteParticipante(id);
		return "redirect:/visualizar/" + comisionId;
	}
}
