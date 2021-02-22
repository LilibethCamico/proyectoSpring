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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {
	
	/*@PrePersist
	public void prePersist() {
		createAt=new Date();
	}*/
	
	public Cliente() {
		facturas= new ArrayList<Factura>();
	}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public void addFactura(Factura factura) {
	  facturas.add(factura);
	}
	

	@Override
	public String toString() {
		return nombre + nombre + " " + apellido ;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String apellido;
	
	@NotEmpty
	@Email
	@Column(name = "correo")
	private String email;

	@NotNull
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") //damos formato a la hora en el Json
	private Date createAt;
	
	private String foto;
	
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY , cascade = CascadeType.ALL, orphanRemoval = true)
	/*@JsonIgnore la usamos para cuando se hace la conversion, no arroge como ese bucle infinito por la relacion
	es como el @XmlTransient que lo omite pero este se usa con Json, aqui omitimos a factura, ya que la misma continiene al cliente*/ 
	
	/*con @JsonMangedReference indicamos la relacion unidireccional , es decir de Cliente a Factura y no al reves, para evitar el bucle, 
	 * asi que cambiamos de @JsonIgnore por esta, por que la idea es mostrar la factura y no tener que ignorarla*/
	@JsonManagedReference  /*y en factura hacemos la contraparte, indicamos que no la queremos mostrar. Lo que 
	quiere decir, que es la parte delante la que se serializa notmalmente en el Json, es la que se va a 
	mostrar en nuestro documento*/
	private List<Factura> facturas; 

	private static final long serialVersionUID = 1L;
	
    
}
