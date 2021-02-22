package com.camico.springboot.proyect.app.view.xlsx;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.camico.springboot.proyect.app.models.entity.Factura;
import com.camico.springboot.proyect.app.models.entity.ItemFactura;

@Component("factura/ver.xlsx")  /*aqui como hacemos referencia a la vista factura/ver y debe ser unico el nombre, la cualdebemos hacer una 
diferencia entre una y otra y lo marcamos con su extencion a usar .xlsx*/
public class FacturaXlsxView extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//para dar nombre al excel
		response.setHeader("Content-Disposition", "attachment; filename=\"factura_ver.xlsx\"");
		
		// aqui en vez de Document(pdf) es la planilla de excel(workbook)
		Factura factura = (Factura) model.get("factura");  //se debe llamar igual a como lo pasamos en el controlador en ver
		Sheet sheet= workbook.createSheet();
		
		/*Aplicamos la forma sencilla a traves de una super clase,la cual retorna un tipo MeessageSourceAccesor 
		 * ya que utiliza el Locale por debajo, detras de escenario e igual podemospasar los mensajes y traducir*/
		MessageSourceAccessor mensajes= getMessageSourceAccessor();
		
		//fila para los datos del cliente
		Row row=sheet.createRow(0); //primera fila
		Cell cell =row.createCell(0);
		cell.setCellValue(mensajes.getMessage("text.factura.ver.datos.cliente"));
		
		row=sheet.createRow(1); //segunda fila
		cell=row.createCell(0); //columna cero de la segunda fila
		cell.setCellValue(factura.getCliente().getNombre() +  " " + factura.getCliente().getApellido());
		
		row =sheet.createRow(2); //tercera fila
		cell =row.createCell(0); 
		cell.setCellValue(factura.getCliente().getEmail());
		
		/*Encadenando metodos, mas simple y sencilla de crear row y cell, llamamos directamente a los metodos*/
		sheet.createRow(4).createCell(0).setCellValue(mensajes.getMessage("text.factura.ver.datos.factura"));
		sheet.createRow(5).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.folio") + ": " + factura.getId());
		sheet.createRow(6).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.descripcion") + ": " + factura.getDescripcion());
		sheet.createRow(7).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.fecha") + ": " + factura.getCreateAt());
		
		//para dar estilos a las celdas
		CellStyle theaderStyle=workbook.createCellStyle();
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle tbodyStyle=workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		
		//Detalle de la factura
		Row header= sheet.createRow(9);
		header.createCell(0).setCellValue(mensajes.getMessage("text.factura.form.item.nombre"));
		header.createCell(1).setCellValue(mensajes.getMessage("text.factura.form.item.precio"));
		header.createCell(2).setCellValue(mensajes.getMessage("text.factura.form.item.cantidad"));
		header.createCell(3).setCellValue(mensajes.getMessage("text.factura.form.item.total"));
		
		//agregamos los estilos a los nombres mencionados anteriormente
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);
		
		int rownum= 10; //apartir de la fila 10, empieza a iterar con respecto a cada celda
		for(ItemFactura item: factura.getItems()) {
			Row fila=sheet.createRow(rownum++);
			
			cell=fila.createCell(0);
			cell.setCellValue(item.getProducto().getNombre());
			cell.setCellStyle(tbodyStyle);
			
			cell=fila.createCell(1);
			cell.setCellValue(item.getProducto().getPrecio());
			cell.setCellStyle(tbodyStyle);
			
			cell=fila.createCell(2);
			cell.setCellValue(item.getCantidad());
			cell.setCellStyle(tbodyStyle);
			
			cell=fila.createCell(3);
			cell.setCellValue(item.calcularImporte());
			cell.setCellStyle(tbodyStyle);
		}
		
		//agregamos otra fila para el gran total
		Row filatotal=sheet.createRow(rownum);
		
		cell= filatotal.createCell(2);
		cell.setCellValue(mensajes.getMessage("text.factura.form.total") + ": ");
		cell.setCellStyle(tbodyStyle);
		
		cell= filatotal.createCell(3);
		cell.setCellValue(factura.getTotal());
		cell.setCellStyle(tbodyStyle);
		
	}

}
