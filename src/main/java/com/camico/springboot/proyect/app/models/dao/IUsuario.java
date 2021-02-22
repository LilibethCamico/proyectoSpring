package com.camico.springboot.proyect.app.models.dao;

import org.springframework.data.repository.CrudRepository;
import com.camico.springboot.proyect.app.models.entity.Usuario;


public interface IUsuario extends CrudRepository<Usuario, Long>{
	
	/*implementamos un metodo personalizado, que retorna un usuario, la cual se esdtara ejecutrando detras 
	 * de escenario la consulta JPQL: "select u from Usuario u where u.username=?1" o esta misma consulta 
	 * reflejarla con anotacion @Query en el metodo pero asi esta mas que bien.
	 */

	public Usuario findByUsername(String username); //consulta a traves del nombre del metodo, la cual reemplaza lo que hicimos con JDBC

}
