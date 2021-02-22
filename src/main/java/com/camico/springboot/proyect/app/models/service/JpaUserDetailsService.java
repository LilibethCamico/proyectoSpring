package com.camico.springboot.proyect.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camico.springboot.proyect.app.models.dao.IUsuario;
import com.camico.springboot.proyect.app.models.entity.Role;
import com.camico.springboot.proyect.app.models.entity.Usuario;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService{
	
	@Autowired
	private IUsuario usuarioDao;
	
	private Logger logger=LoggerFactory.getLogger(JpaUserDetailsService.class);

	@Override
	@Transactional(readOnly = true) //como hara una consulta es necesario colocar el transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Cargar el usuario a traves de username, nos la provee la interfaz implementada.
		
		Usuario usuario=usuarioDao.findByUsername(username); //obtenemos al usuario
		
		//se puede validar si es nullo, que no exista en la BBDD
		if (usuario == null) {
			logger.error("Error loggin: no existe el usuario '" + username + "'");
			throw new UsernameNotFoundException("Username " + username + "no existeen en el sistema");
		}
		
		//obtener los roles, y registrarlo dentro de una lista GrantedAuthority
		List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
		
		for (Role role : usuario.getRoles()) {
			logger.info("Role: ".concat(role.getAuthority()));
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		
		//validar en caso que exista pero que no tenga roles asignados
		if (authorities.isEmpty()) { //que no venga vacio 
			logger.error("Error loggin: nusuario '" + username + "' no tiene roles asignado!");
			throw new UsernameNotFoundException("Error loggin: usuario '" + username + "' no tiene roles asignado!");
		}
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	} 

}
