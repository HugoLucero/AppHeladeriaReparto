/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.util;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import ar.com.dalmasso.app.domain.Orden;
import ar.com.dalmasso.app.domain.ProductoVendido;
import ar.com.dalmasso.app.domain.Reporte;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Component("visualizarReporte")
public class ImprimirReporte extends AbstractPdfView {

    /*
    *Clase encargada de emplear la utilidad para exportar los "Reportes".
    *Utilizando DynammicsReports, extraído de un repositorio en Git.
    */

    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document dcmnt, PdfWriter writer, HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        Reporte reporte = (Reporte) map.get("reporte");
        List<ProductoVendido> prod = reporte.getProductos();
        List<Orden> ordenes = reporte.getOrdenes();
        Map<Object, Double> totalProductos = prod.stream()
        .collect(Collectors.groupingBy(ProductoVendido::getNombre, Collectors.summingDouble(p -> p.getCantidad())));

        NumberFormat nf = new DecimalFormat("##.###");

        dcmnt.setPageSize(PageSize.A4);
        dcmnt.setMargins(-20, -20, 0, 0);
        dcmnt.open();

        //Tabla del titulo
        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPCell titulo = null;

        Font fuenteTitulo = FontFactory.getFont("Helvetica", 16, Color.BLACK);

        titulo = new PdfPCell(new Phrase("Control Productos", fuenteTitulo));
        titulo.setBorder(0);
        titulo.setBackgroundColor(Color.lightGray);
        titulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        titulo.setVerticalAlignment(Element.ALIGN_CENTER);
        titulo.setPadding(30);

        tablaTitulo.addCell(titulo);
        tablaTitulo.setSpacingAfter(30);

        //Tabla del Cabecero
        PdfPTable tablaTituloCabecero = new PdfPTable(2);
        tablaTituloCabecero.addCell("FECHA Y HORA: " + reporte.getFechaYHora());
        tablaTituloCabecero.addCell("N° REPORTE: " + reporte.getIdReporte());
        tablaTituloCabecero.setSpacingAfter(20);


        //Tabla de Titulos para tabla producto
        PdfPTable tablaTitulos = new PdfPTable(2);
        tablaTitulos.addCell("PRODUCTOS");
        tablaTitulos.addCell("CANTIDAD");
        tablaTitulos.setSpacingAfter(10);

        //Tabla Productos
        PdfPTable tablaProductos = new PdfPTable(2);
        for (Map.Entry<Object, Double> entry : totalProductos.entrySet()) {
            tablaProductos.addCell( entry.getKey().toString());
            tablaProductos.addCell(nf.format(entry.getValue()));
        }

        //Hoja de Clientes

        PdfPTable tituloCliente = new PdfPTable(4);
        tituloCliente.setSpacingAfter(10);
        tituloCliente.addCell("CLIENTE");
        tituloCliente.addCell("TOTAL");
        tituloCliente.addCell("PAGADO");
        tituloCliente.addCell("DEBE");
        tituloCliente.setSpacingAfter(10);

        PdfPTable tablaClientes = new PdfPTable(4);
           ordenes.forEach(order -> {
               order.getClientes().forEach(cliente -> {
                   tablaClientes.addCell(cliente.getNombre() + " " + cliente.getApellido());
               });
               tablaClientes.addCell(order.getTotal().toString());
                tablaClientes.addCell(" ");
                tablaClientes.addCell(" ");
           });

        //Insercion al documento
        dcmnt.addTitle("Reporte");

        dcmnt.add(tablaTitulo);
        dcmnt.add(tablaTituloCabecero);
        dcmnt.add(tablaTitulos);
        dcmnt.add(tablaProductos);
        dcmnt.newPage();
        dcmnt.add(tituloCliente);
        dcmnt.add(tablaClientes);

    }

}
