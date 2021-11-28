/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.util;

import ar.com.dalmasso.app.domain.ClienteAgregado;
import ar.com.dalmasso.app.domain.Orden;
import ar.com.dalmasso.app.domain.ProductoVendido;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Component("visualizarOrden")
public class ReporteVenta extends AbstractPdfView {

    /*
     * Clase encargada de emplear la utilidad para exportar los "Reportes".
     * Utilizando DynammicsReports, extraído de un repositorio en Git.
     */

    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document dcmnt, PdfWriter writer, HttpServletRequest hsr,
            HttpServletResponse hsr1) throws Exception {
        Orden orden = (Orden) map.get("orden");
        List<ProductoVendido> productos = orden.getProductos();
        List<ClienteAgregado> clientes = orden.getClientes();

        NumberFormat nf = new DecimalFormat("##.###");


        dcmnt.setPageSize(PageSize.A4);
        dcmnt.setMargins(-20, -20, 0, 0);
        dcmnt.open();

        // Tabla del titulo
        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPCell titulo = null;

        Font fuenteTitulo = FontFactory.getFont("Helvetica", 16, Color.BLACK);

        titulo = new PdfPCell(new Phrase("Reporte de Venta", fuenteTitulo));
        titulo.setBorder(0);
        titulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        titulo.setVerticalAlignment(Element.ALIGN_CENTER);
        titulo.setPadding(10);

        tablaTitulo.addCell(titulo);
        tablaTitulo.setSpacingAfter(20);

        // Tabla del Cabecero
        PdfPTable tablaTituloCabecero = new PdfPTable(3);
        tablaTituloCabecero.addCell("FECHA Y HORA");
        tablaTituloCabecero.addCell("CLIENTE");
        tablaTituloCabecero.addCell("N° VENTA");
        tablaTituloCabecero.setSpacingAfter(5);

        PdfPTable tablaCabecero = new PdfPTable(3);
        tablaCabecero.addCell(orden.getFechaYHora());
        clientes.forEach(cliente -> {
            tablaCabecero.addCell(cliente.getNombre() + " " + cliente.getApellido());
        });
        tablaCabecero.addCell(orden.getIdOrden().toString());
        tablaCabecero.setSpacingAfter(15);

        // Tabla de Titulos para tabla producto
        PdfPTable tablaTitulos = new PdfPTable(4);
        tablaTitulos.addCell("NOMBRE");
        tablaTitulos.addCell("CANTIDAD");
        tablaTitulos.addCell("PRECIO");
        tablaTitulos.addCell("SUBTOTAL");
        tablaTitulos.setSpacingAfter(10);

        // Tabla Productos
        PdfPTable tablaProductos = new PdfPTable(4);
        productos.forEach(producto -> {
            tablaProductos.addCell(producto.getNombre());
            tablaProductos.addCell(nf.format(producto.getCantidad()));
            tablaProductos.addCell(producto.getPrecio().toString());
            tablaProductos.addCell(producto.getTotal().toString());
            tablaProductos.setSpacingAfter(10);
        });

        //Tabla del total
        PdfPTable tablaTotal = new PdfPTable(1);
        PdfPCell total = null;

        Font fuenteTotal = FontFactory.getFont("Helvetica", 16, Color.BLACK);

        total = new PdfPCell(new Phrase("Total: " + orden.getTotal(), fuenteTotal));
        total.setBorder(1);
        total.setHorizontalAlignment(Element.ALIGN_RIGHT);
        total.setVerticalAlignment(Element.ALIGN_RIGHT);
        total.setPadding(5);

        tablaTotal.addCell(total);
        tablaTotal.setSpacingAfter(30);

        // Insercion al documento
        dcmnt.addTitle("Reporte de Venta");

        dcmnt.add(tablaTitulo);
        dcmnt.add(tablaTituloCabecero);
        dcmnt.add(tablaCabecero);
        dcmnt.add(tablaTitulos);
        dcmnt.add(tablaProductos);
        dcmnt.add(tablaTotal);

        // Repetimos el reporte para obtener un duplicado dentro
        // de la misma hoja.
        dcmnt.add(tablaTitulo);
        dcmnt.add(tablaTituloCabecero);
        dcmnt.add(tablaCabecero);
        dcmnt.add(tablaTitulos);
        dcmnt.add(tablaProductos);
        dcmnt.add(tablaTotal);

    }

}
