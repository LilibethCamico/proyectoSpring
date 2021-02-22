package com.camico.springboot.proyect.app.view.csv;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.camico.springboot.proyect.app.models.entity.Cliente;

@Component("listar.csv") //colocamos el nombre la vista, como le llamamos en el ClienteController
public class ClienteCsvView extends AbstractView{
	
	/*aplicado al listado de los clientes por que el 99% de las veces se utiliza para listar elementos y no para un detalle, 
	 * aqui como spring no maneja una clase AbstractCsv como las heredadas en pdf y xlsx, tenemos que usar nuestra propia vista, 
	 * y lo hacemos con una clase mas especifica, mas abstracta AbstractView */
	

	public ClienteCsvView() {
	  setContentType("text/csv");
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true; //true por que genera un archivo que es descargable
	} 
	
	//como es un archivo que se descarga debemos sobreescribir un metodo para generar la descarga
	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {//similar a lo que hicimos con pdf y xlsx debemos de envolverlo el contenido en la respuesta
		
		//aqui cambiamos la respuesta, para asignar un nombre a nuestro archivo plano
		response.setHeader("Content-Disposition", "attachment; filename=\"clientes.csv\"");
		response.setContentType(getContentType()); //pasamos el contentType
	
		Page<Cliente> cliente =(Page<Cliente>) model.get("cliente"); 
		//tal cual se esta pasando en el ClienteController, y retorna un object tipo Page se debe hacer un cast
		
		ICsvBeanWriter beanWriter= new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		//con preference seria por defecto, es como el formato que se da a la hora de guardarlo con los separadores , ; u otros dependiendo de la configuracion
	
		//debemos saber que datos incluir y debemos tener el header del archivo plano
		String[] header= {"id", "nombre", "apellido" , "email", "createAt"};
		
		//la cual escribimos una linea para el header
		beanWriter.writeHeader(header);
		
		for(Cliente clientes: cliente) {
			beanWriter.write(clientes, header);
		}
		beanWriter.close(); //cerrar el recuerso
	
	}

	
	

	


}
