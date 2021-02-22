package com.camico.springboot.proyect.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.camico.springboot.proyect.app.models.entity.Cliente;

public interface ICliente extends PagingAndSortingRepository<Cliente, Long>{
	
	/*para optimizar la busqueda, sin carga perezosa relacionadas con join, con join se debe cumplir 
	 * que ambas tablas tengan datos, pero para que no diga que no existe el id del cliente cuando se va a ver 
	 * el detalle del mismo existiendo cuando no tenga factura, debemos agregar a la consulta un left y se pueda mostrar
	 * el cliente a pesar de que no tenga facturas asignadas*/
	
	@Query("select c from Cliente c left join fetch c.facturas f where c.id=?1")
	public Cliente fetchByIdWithFacturas(Long id);

}
