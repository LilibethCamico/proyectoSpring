package com.camico.springboot.proyect.app.controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocaleController {
	
	@GetMapping("/locale")
	public String locale(HttpServletRequest request) {
		String ultimaUrl= request.getHeader("referer");/*pasamos el nombre del parametro que queremos obtener en este 
		caso referer, la cual nos entrega la referencia de la ultima url*/
	
		return "redirect:".concat(ultimaUrl);
	}

}
