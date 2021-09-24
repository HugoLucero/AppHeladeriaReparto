/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.web;

import ar.com.dalmasso.app.dao.ProductoVendidoDao;
import ar.com.dalmasso.app.domain.Cliente;
import ar.com.dalmasso.app.domain.Orden;
import ar.com.dalmasso.app.domain.ProductoVendido;
import ar.com.dalmasso.app.domain.Reporte;
import ar.com.dalmasso.app.service.IClienteService;
import ar.com.dalmasso.app.service.IOrdenService;
import ar.com.dalmasso.app.service.IReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Controller
@RequestMapping(path = "/reporte")
public class ControllerReporte {

    /**
     * Este es el controlador encargado de crear los reportes.
     * Se utilizó una session para poder agregar o quitar las ordenes
     * en el momento que se crea el reporte y así tener un mayor
     * control del mismo.
     */

    @Autowired
    private IOrdenService ordenService;
    @Autowired
    private IReporteService reporteService;
    @Autowired
    private IClienteService clienteService;
    @Autowired
    private ProductoVendidoDao productoService;

    @PostMapping(value = "/quitarOrden/{indice}")
    public String quitarDelReporte(@PathVariable int indice, HttpServletRequest request) {
        ArrayList<Orden> informe = this.obtenerOrdenes(request);
        if (informe != null && informe.size() > 0 && informe.get(indice) != null) {
            informe.remove(indice);
            this.guardarInforme(informe, request);
        }
        return "redirect:/reporte/";
    }

    private void limpiarIInforme(HttpServletRequest request) {
        this.guardarInforme(new ArrayList<>(), request);
    }

    @GetMapping(value = "/limpiarInforme")
    public String cancelarReporte(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        this.limpiarIInforme(request);
        redirectAttrs
                .addFlashAttribute("mensaje", "Reporte cancelado")
                .addFlashAttribute("clase", "info");
        return "redirect:/reporte/";
    }

    @PostMapping(value = "/terminarInforme")
    public String terminarReporte(Reporte reporte, Orden orden, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        ArrayList<Orden> informe = this.obtenerOrdenes(request);
        // Si no hay informe o está vací­o, regresamos inmediatamente
        if (informe == null || informe.size() <= 0) {
            return "redirect:/reporte/";
        }
        //Guardamos el Reporte para poder obtener su ID
        reporteService.guardar(reporte);
        //Iteramos todas las ordenes agregadas a la session
        for (Orden ordenAgregar : informe) {
            Orden o = ordenService.encontrarOrden(ordenAgregar);
            if (o == null) {
                continue;
            } else {
                //Agremos el ID del Reporte.
                ordenAgregar.setReporte(reporte);
            }
            ordenService.guardar(ordenAgregar);
            //Seteamos el ID del reporte a los productos vendidos.
            for (ProductoVendido productos : ordenAgregar.getProductos()) {
                productos.setReporte(reporte);
                productoService.save(productos);
            }
        }

        // Al final limpiamos el reporte
        this.limpiarIInforme(request); 
        // e indicamos la creación exitosa
        redirectAttrs
                .addFlashAttribute("mensaje", "Reporte creado correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/reporte/";
    }

    @GetMapping(value = "/")
    public String interfazAgregar(Orden orden, BindingResult bindingResult, Cliente cliente, Model model, HttpServletRequest request) {
        List<Orden> ordenes = ordenService.listarOrdenes();
        Collections.sort(ordenes, new Comparator<Orden>() {

            @Override
            public int compare(Orden o1, Orden o2) {
                return o1.getFechaYHora().compareToIgnoreCase(o2.getFechaYHora());
            }

        }.reversed());
        model.addAttribute("listarOrdenes", ordenes);
        var clientes = clienteService.listarClientes();
        model.addAttribute("clienteOrden", clientes);
        model.addAttribute("orden", orden);
        return "reporte/reporte";
    }

    private ArrayList<Orden> obtenerOrdenes(HttpServletRequest request) {
        ArrayList<Orden> informe = (ArrayList<Orden>) request.getSession().getAttribute("informe");
        if (informe == null) {
            informe = new ArrayList<>();
        }
        return informe;
    }

    private void guardarInforme(ArrayList<Orden> informe, HttpServletRequest request) {
        request.getSession().setAttribute("informe", informe);
    }

    @PostMapping(value = "/agregarOrden")
    public String agregarAlReporte(@ModelAttribute Orden orden, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        ArrayList<Orden> informe = this.obtenerOrdenes(request);
        Orden ordenBuscadaPorId = ordenService.encontrarOrden(orden);
        if (ordenBuscadaPorId == null) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Seleccione una orden de la lista.")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/reporte/";
        }
        informe.add(ordenBuscadaPorId);
        this.guardarInforme(informe, request);
        return "redirect:/reporte/";
    }
}
