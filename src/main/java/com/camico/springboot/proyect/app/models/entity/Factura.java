package com.camico.springboot.proyect.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {
	

	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

	public Factura() {
		this.items = new ArrayList<ItemFactura>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	/* cuando estamos conviertiendo a XML se produce un error al crearlo, pero es debido a la relacion que tiene con factura 
	 * y hay que especificar un solo lado, es decir para que no vaya ern la direccion del cliente sino hacia adelante continue 
	 * y no se vuelva infinito sino en una sola direccion, lo hacemos desde el metodo getCliente, CON @XmlTransient la palabra 
	 * Transent significa que cuando se serializa no va a llamar a este metodo, no lo incluye
	 * 	 * ERROR: Se ha detectado un ciclo en el gráfico de objeto. Esto provocará un XML con profundidad infinita
	 */
	@XmlTransient
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}

	public void addItemFactura(ItemFactura item) {
		this.items.add(item);
	}

	public Double getTotal() {
		Double total=0.0;
		
		int size=items.size(); //devuelve el tama;o de los items
	
		for(int i=0; i<size; i++) {
			total +=items.get(i).calcularImporte(); //cantidad*producto lo sumara en cada items,y se asigna a total
		}
		return total; //la cual contiene la sumatoria completa de la factura
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String descripcion;
	private String observacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	private Date createAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference /*aqui hacemos la contgraparte con respecto a @JsonManagedReferen que hicimos en Cliente.
	Sin embargo esta anotacion, es la parte posterior de la relacion y se omitira de la serializacion*/
	private Cliente cliente;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "factura_id") // para la llave foranea en la tabla item_facturas
	private List<ItemFactura> items;

	private static final long serialVersionUID = 1L;

}
