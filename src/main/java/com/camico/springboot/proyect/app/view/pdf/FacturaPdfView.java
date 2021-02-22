package com.camico.springboot.proyect.app.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import com.camico.springboot.proyect.app.models.entity.Factura;
import com.camico.springboot.proyect.app.models.entity.ItemFactura;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("factura/ver.pdf")
public class FacturaPdfView extends AbstractPdfView{
	 /*esta clase debe ser un bean de spring, y le damos componet y para renderizar la 
	vista debe heredar de ViewResolver, pero en nuestro caso vamos a utilizar la clase abstracta AbstractPdfView*/
	
	//Para aplicar la traduccion de los textos en la vista, aplicamos lo mismo que hicimos en LoginSuccessHandler para pasar los menssage
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
    private LocaleResolver localeResolver;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Factura factura=(Factura) model.get("factura"); /*hace un cast por que retorna un objecto, y se 
		convierte al objeto real, aqui se debe llamar igual con el nombre que se guarda en el controllador*/
		
		Locale locale = localeResolver.resolveLocale(request); //inyectamos el localeResolver
		
		/*otra forma sencilla de pasarlo es a traves de una super clase,la cual retorna un tipo MeessageSourceAccesor 
		 * ya que utiliza el Locale por debajo, detras de escenario e igual podemospasar los mensajes y traducir*/
		MessageSourceAccessor mensajes= getMessageSourceAccessor();
		
		PdfPTable tabla= new PdfPTable(1);
		tabla.setSpacingAfter(20);
		
		PdfPCell cell= null;
		cell=new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.cliente", null, locale)));
		cell.setBackgroundColor(new Color(184, 218, 255)); //azul pastel
		cell.setPadding(8f);
		
		tabla.addCell(cell);
		tabla.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		tabla.addCell(factura.getCliente().getEmail());
		
		PdfPTable tabla2= new PdfPTable(1);
		tabla2.setSpacingAfter(20);
		
		
		cell=new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.factura", null, locale)));
		cell.setBackgroundColor(new Color(195, 230, 203)); //azul pastel
		cell.setPadding(8f);
		tabla2.addCell(cell);
		tabla2.addCell(mensajes.getMessage("text.cliente.factura.folio") + ": " + factura.getId()); //forma de pasarlo con MessageSourceAccesor
		tabla2.addCell(mensajes.getMessage("text.cliente.factura.descripcion") + ": " + factura.getDescripcion());
		tabla2.addCell(mensajes.getMessage("text.cliente.factura.fecha") + ": " + factura.getCreateAt());
		
		document.add(tabla);
		document.add(tabla2);
		
		PdfPTable tabla3=new PdfPTable(4);
		tabla3.setWidths(new float [] {3.5f, 1,1,1}); //medidas de relaticas entre ellas
		tabla3.addCell(mensajes.getMessage("text.factura.form.item.nombre"));
		tabla3.addCell(mensajes.getMessage("text.factura.form.item.precio"));
		tabla3.addCell(mensajes.getMessage("text.factura.form.item.cantidad"));
		tabla3.addCell(mensajes.getMessage("text.factura.form.item.total"));
		
		//creamos las lineas(items) por cada detalle
		for(ItemFactura item: factura.getItems()) {
			tabla3.addCell(item.getProducto().getNombre());
			tabla3.addCell(item.getProducto().getPrecio().toString()); //como es un integer, debemos convertir a string
			
			cell= new PdfPCell(new Phrase(item.getCantidad().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); //para centrar la celda cantidad
			tabla3.addCell(cell);
			tabla3.addCell(item.calcularImporte().toString());		
		}
		
		cell= new PdfPCell(new Phrase(mensajes.getMessage("text.factura.form.total") + ": "));
		cell.setColspan(3); //añadimos 3 espacio indicados en la tabla3 por producto, precio cantidad
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		tabla3.addCell(cell); //pasamos la celda a la tabla3
		tabla3.addCell(factura.getTotal().toString()); //para Total si es directo
		
		document.add(tabla3);
		
	}

}
