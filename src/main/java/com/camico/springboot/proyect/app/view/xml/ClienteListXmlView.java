package com.camico.springboot.proyect.app.view.xml;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.camico.springboot.proyect.app.models.entity.Cliente;

@Component("listar.xml")
public class ClienteListXmlView extends MarshallingView{
	//esta clase vendria siendo como la vista
	
	 
	@Autowired //Anterior se creo una clase en MvcConfig, inyectamos el bean que va a envolver la clase para la conversion
	public ClienteListXmlView(Jaxb2Marshaller marshaller) {
		super(marshaller);
	} 
	
	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		/* Como en el ClienteController se pasa el titulo, cliente y page, aqui solo debemos obtener el listado de cliente, 
		 * por ender los eliminamos para no tenerlos dentro del xml y obtenemos
		 * model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
		model.addAttribute("cliente", cliente);
		model.addAttribute("page", pageRender);
		 */
		
		model.remove("titulo");
		model.remove("page");
		
		//pero al cliente antes de eliminarlo debemos obtener a los mismos para convertilos
		Page<Cliente> cliente =(Page<Cliente>) model.get("cliente"); 
		
		model.remove("cliente");
		
		/*despues guardamos en el model el ClienteListXml junto al listado de clientes, 
		 * la consulta se hara con los clientes paginados, pero para que funcione debemos crear la instancia de la clase que lo va a envolver*/
		model.put("ClienteListXml", new ClienteListXml(cliente.getContent())); 
		
		super.renderMergedOutputModel(model, request, response); //se deja igual por que esta llamando al objecto del padre
	}


	
	
	
  
}
