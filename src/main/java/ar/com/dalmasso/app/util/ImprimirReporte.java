/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.util;

import ar.com.dalmasso.app.domain.Orden;
import ar.com.dalmasso.app.domain.ProductoVendido;
import ar.com.dalmasso.app.domain.Reporte;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        PdfPTable totalesData = new PdfPTable(1);
        totalesData.addCell("TOTAL CLIENTES: " + ordenes.stream().map(o -> o.getClientes().size()).count());
        totalesData.setSpacingAfter(10);

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

        PdfPTable tituloCliente = new PdfPTable(5);
        tituloCliente.setSpacingBefore(10);
        tituloCliente.addCell("#");
        tituloCliente.addCell("CLIENTE");
        tituloCliente.addCell("TOTAL");
        tituloCliente.addCell("PAGADO");
        tituloCliente.addCell("DEBE");
        tituloCliente.setSpacingAfter(10);

        PdfPTable tablaClientes = new PdfPTable(5);
            ordenes.forEach(order -> {
                tablaClientes.addCell(String.valueOf(ordenes.indexOf(order)+1));
               order.getClientes().forEach(cliente -> {
                   tablaClientes.addCell(cliente.getNombre());
               });
               tablaClientes.addCell(order.getTotal().toString());
                tablaClientes.addCell(" ");
                tablaClientes.addCell(" ");
           });

           PdfPTable tablaTotal = new PdfPTable(1);
           PdfPCell totales = null;

           Font fuenteTotal = FontFactory.getFont("Arial", 16, Color.BLACK);

           totales = new PdfPCell(new Phrase("TOTAL: $" + nf.format(reporte.getTotal()), fuenteTotal));
           totales.setBorder(1);
           totales.setHorizontalAlignment(Element.ALIGN_RIGHT);
           totales.setVerticalAlignment(Element.ALIGN_RIGHT);
           totales.setPadding(5);

           tablaTotal.addCell(totales);
           tablaTotal.setSpacingBefore(20);

        //Insercion al documento
        dcmnt.addTitle("Reporte");

        dcmnt.add(tablaTitulo);
        dcmnt.add(tablaTituloCabecero);
        dcmnt.add(totalesData);
        dcmnt.add(tablaTitulos);
        dcmnt.add(tablaProductos);
        dcmnt.newPage();
        dcmnt.add(tituloCliente);
        dcmnt.add(tablaClientes);
        dcmnt.add(tablaTotal);

    }

}
