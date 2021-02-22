package com.camico.springboot.proyect.app.controllers;

import java.security.Principal;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	@Autowired
    private MessageSource messageSource;

	@GetMapping("/login")
	public String login(@RequestParam(value="error", required = false) String error, 
			@RequestParam(value="logout", required = false) String logout, 
			//con RequestParam capturamos lo que nos envia spring cuando no existe un error o logout
			Model model, Principal principal, RedirectAttributes flash, Locale locale) {

		if (principal != null) { // se usa para validar, si ya a iniciado sesion y se evita a aque se haga 2 veces
			flash.addFlashAttribute("info", messageSource.getMessage("text.login.already", null, locale));
			return "redirect:/";
		}
		if (error != null) {
			model.addAttribute("error", messageSource.getMessage("text.login.error", null, locale));
		}
		if (logout !=null) {
			model.addAttribute("success", messageSource.getMessage("text.login.logout", null, locale));
		}
		return "login";
	}

}
