package com.camico.springboot.proyect.app.view.xml;
/*Este nombre de paquete es la que se va colocar en la clase que la va a envolver osea en el bean Jxb2Mashaller en la clase de configuracion*/
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.camico.springboot.proyect.app.models.entity.Cliente;

@XmlRootElement(name = "clientes") 
/*para indicar que es la clase rootXml, sino se le asigna un nombre toma por defecto el nombre de la clase*/
public class ClienteListXml {
	//muy simple la cual tendra un atributo de Listado de Clientes, lka cual es lo que vamos a convertir a Xml
	
	public ClienteListXml() {} //constructor por defecto
	public ClienteListXml(List<Cliente> clientes) {
		super();
		this.clientes = clientes;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	@XmlElement(name = "cliente") 
	/*Indicamos que este sera el elemento a convertir, y es en singular para especificar en el documento XML por cada uno*/
	public List<Cliente> clientes;

	
}
