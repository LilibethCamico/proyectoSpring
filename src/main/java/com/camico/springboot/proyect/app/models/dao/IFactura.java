package com.camico.springboot.proyect.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.camico.springboot.proyect.app.models.entity.Factura;


public interface IFactura extends CrudRepository<Factura, Long>{
	
	//para optimizar la busqueda, para que vegan todas las relaciones de una sola vez y no por separado.
	@Query("select f from Factura f join fetch f.cliente c join fetch f.items l join fetch l.producto where f.id=?1")
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);

}
