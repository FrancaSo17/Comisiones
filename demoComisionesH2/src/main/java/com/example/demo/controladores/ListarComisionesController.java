package com.example.demo.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.data.Comision;
import com.example.demo.data.Participante;
import com.example.demo.data.Usuario;
import com.example.demo.servicios.IServicioComision;
import com.example.demo.servicios.IServicioParticipantes;
import com.example.demo.servicios.IServicioUsuario;

import jakarta.servlet.http.HttpSession;

@Controller
public class ListarComisionesController {

	@Autowired
	IServicioComision comisionServicio;

	@Autowired
	IServicioParticipantes participanteServicio;

	@Autowired
	IServicioUsuario usuarioServicio;

//	@Autowired
//	IServicioEstado estadoServicio;

	

	@GetMapping(value={"/listar"})
	public String listar(Model model,HttpSession session) {
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		List<Comision> comisiones = comisionServicio.getAllComsiones();

		List<Comision> comisionesSinVerificar = comisionServicio.getAllcomisionesSinVerificar(usuario);

		List<Comision> comisionesSinValidar = comisionServicio.getAllcomisionesSinValidar(usuario);
		
		List<Comision> comisionesCanceladas = comisionServicio.getAllcomisionesCanceladas();
		
		List<Comision> comisionesAprobadas = comisionServicio.getAllcomisionesAprobadas();

//		List<Estado> estados = estadoServicio.getAllEstados();

		model.addAttribute("comisionesSinVerificar", comisionesSinVerificar);

		model.addAttribute("comisionesSinValidar", comisionesSinValidar);
		
		model.addAttribute("comisionesCanceladas", comisionesCanceladas);

		model.addAttribute("comisionesAprobadas", comisionesAprobadas);
		
		model.addAttribute("listar", comisiones);

		
		return "listar";
	}

	@GetMapping(path = "/visualizar/{id}")
	public String visualizarComision(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		Comision comision = comisionServicio.getComisionById(id).get();
		List<Participante> participantes = participanteServicio.getParticipanteByComisionId(id);
		model.addAttribute("comision", comision);
		model.addAttribute("participantes", participantes);
		return "visualizar";
	}

	@GetMapping(path = "/eliminar/{id}")
	public String borrarComision(@PathVariable(name = "id") Integer id) {
		comisionServicio.deleteComision(id);
		return "redirect:/listar";
	}

	@GetMapping(path = "/datosComision/{id}")
	public String mostrarComision(@PathVariable(name = "id") Integer id) {
		comisionServicio.getComisionById(id);
		return "redirect:/visualizar";
	}
	
	
	@GetMapping(path = "/validarComision/{id}")
	public String validarComision(@PathVariable(name = "id") Integer id,HttpSession session ) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		comisionServicio.validarComision(id, usuario);
		return "redirect:/listar";
	}
	
	@GetMapping(path = "/verificarComision/{id}")
	public String verificarComision(@PathVariable(name = "id") Integer id, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		comisionServicio.verificarComision(id, usuario);
		return "redirect:/listar";
	}
	
	//Tiene que llevar a una Pantalla de Motivo de No_Validación
	@GetMapping(path = "/noValidarComision/{id}")
	public String noValidarComision(@PathVariable(name = "id") Integer id, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		comisionServicio.noValidarComision(id, usuario);
		return "redirect:/cancelar/{id}";
	}
	
	//Tiene que llevar a una Pantalla de Motivo de No_Verificación
	@GetMapping(path = "/noVerificarComision/{id}")
	public String noVerificarComision(@PathVariable(name = "id") Integer id, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		comisionServicio.noVerificarComision(id, usuario);
		return "redirect:/noVerificar/{id}";
	}

	// Función para participante

	@GetMapping(path = "/eliminarParticipante/{comisionId}/{id}")
	public String borrarParticipante(@PathVariable(name = "comisionId") Integer comisionId,
			@PathVariable(name = "id") Integer id) {
		participanteServicio.deleteParticipante(id);
		return "redirect:/visualizar/" + comisionId;
	}
}
