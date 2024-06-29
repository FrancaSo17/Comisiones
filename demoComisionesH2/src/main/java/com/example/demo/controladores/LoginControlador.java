package com.example.demo.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.data.Users;
import com.example.demo.data.Usuario;
import com.example.demo.servicios.UsuarioServicio;


@Controller
@RequestMapping(path = "/")
public class LoginControlador {
	
	private final static Logger log = LoggerFactory.getLogger(LoginControlador.class);

	@Autowired
	private UsuarioServicio servicio;
	
	@GetMapping(value={"/","/login"})
	public String login() {
		log.info("GETlogin");
		return "login";
	}
	
	@GetMapping("/registro")
	public String registro(Model model) {
		log.info("GETregistro");
	    model.addAttribute("user", new Users());
	    model.addAttribute("usuario", new Usuario());
	    return "nuevoUsuario";
	}
	
	@PostMapping("/login")
	public String loginSubmit(@RequestParam String username, @RequestParam String password, Model model) {
		log.info("POSTloginSubmit");
		log.info("Procesando login para el usuario: " + username);
	    Usuario usuario = servicio.authenticate(username, password);
	    if (usuario == null) {
	        model.addAttribute("error", "Nombre de usuario o contraseña incorrectos.");
	        return "login";
	    }
	    return "listar";
	}

	 @PostMapping("/registrar")
	public String guardarComision(Users user, Usuario usuario, Model model) {
	   log.info("[POSTGuardarUsuarioController]");
	   log.info("[usuario: ]"+user.getUsername());
	      servicio.registerUser(user,usuario);

	        return "/login";
	    }

}

