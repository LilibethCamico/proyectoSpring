package com.camico.springboot.proyect.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.camico.springboot.proyect.app.models.entity.Cliente;
import com.camico.springboot.proyect.app.models.service.IClienteService;
import com.camico.springboot.proyect.app.models.service.IUploadFileService;
import com.camico.springboot.proyect.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IUploadFileService uploadFileService;
	
	@Autowired
	private MessageSource messageSource; //atraves de este objeto vamos a obtener el idioma 

	
	@Secured("ROLE_USER") /*damos seguridad con esta anotaciones desde el controlador y no desde la clase ante creada 
	SpringSecurityConfig y asi sucesivamente donde tengamos que aplicar la autenticacion con su respectivo ROLE, 
	pero en esta clase donde no estamos dando seguridad debemos habitar el uso de anotaciones para dar seguridad con
	@EnableGlobalMethodSecurity(securedEnabled = true). Esto por que en vez de dar seguridad en las rutas de request, 
	damos seguridad en el controlador usando anotaciones basicamente es lo mismo.*/ 
	@RequestMapping(value = "/uploads/{filename:.+}", method = RequestMethod.GET)
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {// ver foto de forma programatica

		Resource recurso = null;
		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	//@Secured("ROLE_USER")
	//@PreAuthorize("hasRole('ROLE_USER')")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_OTRO')") //tambien podria ser el seguiente para pasar varios roles
	@RequestMapping(value = "/ver/{id}", method = RequestMethod.GET)
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash, Locale locale) {

		//Cliente cliente = clienteService.findOne(id);
		Cliente cliente = clienteService.fethByIdWithFacturas(id); //para optimizar la busqueda con join 
		if (cliente == null) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
			return "redirect:/listar";
		}

		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.detalle.titulo", null, locale).concat(": ").concat(cliente.getNombre()));
		return "ver";
	}

	@RequestMapping(value = {"/listar", "/"}, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model, 
			Authentication authentication, HttpServletRequest request, Locale locale) {

		//aqui el authentication se pasa como argumento del metodo
		if (authentication != null) { //lo obtenemos para visualizar cuando el usuario se autentique
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
		}
		//aqui no se pasa, otra forma de ontenerlo es de forma estatica, sin necesidad de pasarlo como argumento del metodo
		Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) { //lo obtenemos para visualizar cuando el usuario se autentique
			logger.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Hola usuario autenticado, tu username es: ".concat(auth.getName()));
		}
		
		//Primera forma: usamos el metodo hasRole que implementamos para obtener y validar los roles
		if (hasRole("ROLE_ADMIN")) {
			logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}
		
		/* Segunda forma: se crea una instanciua de SpringSecurity que envuelve el objeto http request para validar, otra forma mas simple de chequear la
		 *  autorizacion de Roles en nuestras clase controladoras. La cual para eso pasamos como argumento httpServletRequest
		 */
		SecurityContextHolderAwareRequestWrapper securityContext=new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
	    if (securityContext.isUserInRole("ADMIN")) {
	    	logger.info(" Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info(" Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}
	    
	    // Tercera  forma: Otra forma para obtener y validar los roles con solo el HttpServletRequest, aqui si debemos agregar el prefijo completo
	    if (request.isUserInRole("ROLE_ADMIN")) {
	    	logger.info(" Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info(" Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}
	    
	    
		Pageable pageRequest = PageRequest.of(page, 4);
		Page<Cliente> cliente = clienteService.findAll(pageRequest);

		PageRender<Cliente> pageRender = new PageRender<>("/listar", cliente);

		model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
		model.addAttribute("cliente", cliente);
		model.addAttribute("page", pageRender);
		return "listar";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form")
	public String crear(Model model, Locale locale) {
		Cliente cliente = new Cliente();
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.form.titulo.crear", null, locale));
		model.addAttribute("cliente", cliente);

		return "form";
	}

	//@Secured("ROLE_ADMIN")
	@PreAuthorize("hasRole('ROLE_ADMIN')") 
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash, Locale locale) {

		Cliente cliente = null;

		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.id.error", null, locale));
			return "redirect:/listar";
		}

		model.addAttribute("titulo", messageSource.getMessage("text.cliente.form.titulo.editar", null, locale));
		model.addAttribute("cliente", cliente);
		return "form";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam(name = "file") MultipartFile foto, RedirectAttributes flash, SessionStatus status, Locale locale) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", messageSource.getMessage("text.cliente.form.titulo", null, locale));
			return "form";
		}

		if (!foto.isEmpty()) {
			if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
					&& cliente.getFoto().length() > 0) { // para eliminar foto existente, y reemplazar cuando se edite

				uploadFileService.delete(cliente.getFoto());

			}
			// despues debemos mover la imagen;
			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", messageSource.getMessage("text.cliente.flash.foto.subir.success", null, locale) + "'" + uniqueFilename + "'");
			cliente.setFoto(uniqueFilename);
		}

		String mensajeFlash = (cliente.getId() != null) ? messageSource.getMessage("text.cliente.flash.editar.success", null, locale) : messageSource.getMessage("text.cliente.flash.crear.success", null, locale);

		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash, Locale locale) {

		if (id > 0) {
			Cliente cliente = clienteService.findOne(id);

			clienteService.delete(id);
			flash.addFlashAttribute("success", messageSource.getMessage("text.cliente.flash.eliminar.success", null, locale));

			if (uploadFileService.delete(cliente.getFoto())) {
				/*flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + "eliminada con exito!");*/
				String mensajeFotoEliminar = String.format(messageSource.getMessage("text.cliente.flash.foto.eliminar.success", null, locale), cliente.getFoto());
				flash.addFlashAttribute("info", mensajeFotoEliminar);
			}
		}

		return "redirect:/listar";
	}
	
	//metodo para obtener los roles, que son los authorities desde el controlador de forma estatica con SecurityContextHolder
	private boolean hasRole(String role) {
		
		SecurityContext context= SecurityContextHolder.getContext();
		
		if (context ==null) {
			return false; //no tiene acceso
		}
		Authentication auth=context.getAuthentication();
		if (auth ==null) {
			return false; //no tiene acceso
		}
		//obtenemos la colleccion de los roles
		Collection<? extends GrantedAuthority> authorities= auth.getAuthorities(); //cualquier? clase Role o que representa un Role en nestra aplicacion tiene que implementar esta interface. por eso se implementa tipo generico ?
	    
		for (GrantedAuthority authority: authorities) {
			if (role.equals(authority.getAuthority())) { //si son igual se retorna true
				logger.info("Hola usuario ".concat(auth.getName()).concat(" tu role es: ".concat(authority.getAuthority())));
				return true; //es decir que tiene el rol, tiene permiso
			}
		}
		return false; //y si no lo encuentra, no tiene acceso
	}

}
