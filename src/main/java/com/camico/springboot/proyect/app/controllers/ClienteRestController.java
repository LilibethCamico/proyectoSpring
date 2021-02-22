package com.camico.springboot.proyect.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.camico.springboot.proyect.app.models.service.IClienteService;
import com.camico.springboot.proyect.app.view.xml.ClienteListXml;

@RestController
@RequestMapping("/api/clientes") //para todo nuestros metodos rest
public class ClienteRestController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping(value = "/listar")
    //public @ResponseBody ClienteListXml listar() { se puede quitar aqui el @ResponseBody por que ya lo estamos manejando con @RestController que ya lo trae
	public @ResponseBody ClienteListXml listar() {
		return new ClienteListXml(clienteService.findAll());
	}
}
