/*
 * Software creado y diseñado por Hugo Lucero.
 * Derechos reservados para Hugo Lucero.
 * Para más información contactarse a:
 * hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.web;

import ar.com.dalmasso.app.domain.Cliente;
import ar.com.dalmasso.app.domain.Orden;
import ar.com.dalmasso.app.domain.Producto;
import ar.com.dalmasso.app.domain.ProductoParaVender;
import ar.com.dalmasso.app.service.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Controller
@RequestMapping(path = "/vender")
public class ControllerVenta {

    @Autowired
    private IVentaService ventaService;


    @PostMapping(value = "/quitarCliente/{indice}")
    public String quitarCliente(@PathVariable int indice, HttpServletRequest request) {
       return ventaService.quitarCliente(indice, request);
    }

    @PostMapping(value = "/quitar/{indice}")
    public String quitarDelCarrito(@PathVariable int indice, HttpServletRequest request) {
        return ventaService.quitarDelCarrito(indice, request);
    }

    @GetMapping(value = "/limpiar")
    public String cancelarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        return ventaService.cancelarVenta(request, redirectAttrs);
    }

    @PostMapping(value = "/terminar")
    public String terminarVenta(Orden orden, Model model,
                                @RequestParam(value = "pagado", defaultValue = "false") boolean pagado,
                                @RequestParam(value = "modCant", defaultValue = "0") float modcant,
                                @RequestParam(value = "observaciones", defaultValue = "") String observaciones,
                                HttpServletRequest request,
                                RedirectAttributes redirectAttrs) {
        return ventaService.terminarVenta(orden, model, pagado, modcant, observaciones, request, redirectAttrs);
    }

    @GetMapping(value = "/")
    public String interfazVender(Producto producto, ProductoParaVender ppv, Cliente cliente, Model model, HttpServletRequest request) {
        return ventaService.interfazVender(producto, ppv, cliente, model, request);
    }

    @PostMapping(value = "/agregarAlCarro")
    public String agregarAlCarrito(@RequestParam(value = "cantidad", defaultValue = "1") float cantidad, @ModelAttribute Producto producto,
                                   @RequestParam(value = "idLista", defaultValue = "") Long idLista, HttpServletRequest request, RedirectAttributes redirectAttrs, Model model) {
        return ventaService.agregarAlCarrito(cantidad, producto, idLista, request, redirectAttrs, model);
    }

    @PostMapping(value = "/agregarCliente")
    public String agregarCliente(@ModelAttribute Cliente cliente, Model model, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        return ventaService.agregarCliente(cliente, model, request, redirectAttrs);
    }

    @GetMapping(value = "/prodComAutocomplete")
    @ResponseBody
    public List<String> prodComAutocomplete(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        return ventaService.prodComAutocomplete(term);
    }

    @GetMapping(value = "/clienteAutoComplete")
    @ResponseBody
    public List<String> clienteAutoComplete(@RequestParam(value = "term", required = false, defaultValue="")String term) {
        return ventaService.clienteAutoComplete(term);
    }

    @PostMapping(value = "/modificar")
    public String modificar(@RequestParam(value = "precio", required = false) Float precio, @RequestParam(value = "nombre", required = false, defaultValue = "") String nombre,
                            HttpServletRequest request) {
        return ventaService.modificar(precio, nombre, request);
    }

    @PostMapping(value = "/modificarCant")
    public String modificarCantidad(@RequestParam(value = "modcant", defaultValue = "") Float modcant, @RequestParam(value = "nom", required = false, defaultValue = "") String nom,
                                    HttpServletRequest request) {
        return ventaService.modificarCantidad(modcant, nom, request);
    }
}
