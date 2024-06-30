package com.example.demo.controladores;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.exceptions.ServiceException;
import com.example.demo.data.Comision;
import com.example.demo.data.Participante;
import com.example.demo.servicios.IServicioComision;
import com.example.demo.servicios.IServicioParticipantes;
import com.example.demo.servicios.IServicioUsuario;

@Controller
@RequestMapping(path = "/visualizar")
public class VisualizarComisionController {

	private final static Logger log = LoggerFactory.getLogger(LoginControlador.class);

	@Autowired
	IServicioComision comisionServicio;

	@Autowired
	IServicioParticipantes participanteServicio;

	@Autowired
	IServicioUsuario usuarioServicio;

//	@Autowired
//	IServicioEstado estadoServicio;

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
}
