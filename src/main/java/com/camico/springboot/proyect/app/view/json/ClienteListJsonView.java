package com.camico.springboot.proyect.app.view.json;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.camico.springboot.proyect.app.models.entity.Cliente;

@Component("listar.json") //nombre de vista igual que le dimos en el ontrolador como hicimos con las demas, pero diferenciando con la extension
public class ClienteListJsonView extends MappingJackson2JsonView{

	
	/*Con Json se maneja de manera automatica la configuracion, no se necesita agregar dependencias, ni crear 
	 * clase wrapper para envolverla como se hizo con XML, se realiza perfecto los objectos de coleccion 
	 * simplemente es retornar una vista de cliente heredando de MappingJackson2JsonView
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	protected Object filterModel(Map<String, Object> model) {
		/*Para filtrar o quitar algunos elementos que pasamos a la vista desde el ClienteController, como hicimos en la conversion de Xml
		que quitamos titulo y page, solo obtener el listado y convertirlo*/
		
		
		model.remove("titulo");
		model.remove("page");
		
		//pero al cliente antes de eliminarlo debemos obtener a los mismos para convertilos, como hicimos con Xml
		Page<Cliente> cliente =(Page<Cliente>) model.get("cliente"); 
				
	    model.remove("cliente");
	    model.put("cliente", cliente.getContent()); //sin clase wrapper por que ya lo hace automatico spring
		return super.filterModel(model);
	}

}
