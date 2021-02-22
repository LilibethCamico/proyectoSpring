package com.camico.springboot.proyect.app.models.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "facturas_items")
public class ItemFactura implements Serializable {

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public Double calcularImporte() {
		return cantidad.doubleValue()* producto.getPrecio();
	}
	

	public Producto getProducto() { 
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	private Integer cantidad;
	
	@ManyToOne(fetch = FetchType.LAZY) 
	/*Para la conversion del Json, da un error y lanzando un atributo handler en producto 
	 * la cual producto no lo continiene pero en este formato Json si lo tiene, y esto esta relacionado con la carga peresoza por que hay propiedades que no se pueden
	 *  serialiozar de forma correcta, ya que se mantiene como un proxy y no del tipo original, es un proxy que hereda de producto. Como handler, Hibernate, Inicializer, y otros
	@ManyToOne(fetch = FetchType.EAGER)  //esta es una solucion poco practica pero soluciona es cambiar LAZY por EAGER, la cual trae los productos inmediatamente y no perezosa*/
	
	@JoinColumn(name = "producto_id")
	@JsonIgnoreProperties ({"hibernateLazyInitializer", "handler"}) //puede ir aqui en el atributo o en la clase Producto
	/*para no dejar la carga de inmediata para no traer tablas inecesarias es recomendable esta anotacion, 
	la cual va a ignorar algunas propiedades del atributo producto*/
	private Producto producto;
	private static final long serialVersionUID = 1L;

}
