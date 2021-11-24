/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.web;

import ar.com.dalmasso.app.domain.Producto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import ar.com.dalmasso.app.service.IListasService;
import ar.com.dalmasso.app.service.IProductoService;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Slf4j
@Controller
public class ControllerProductos {

    @Autowired
    private IProductoService productoService;
    @Autowired
    private IListasService listasService;

    //Creamos las variables para Producto
    @GetMapping("/productosInicio")
    public String inicio(Model model) {
        List<Producto> productos = productoService.listarProductos();
        //Utilizamos el SORT para ordenar alfabéticamente la lista de productos.
        Collections.sort(productos, new Comparator<Producto>(){
            @Override
            public int compare(Producto p1, Producto p2){
                return p1.getNombre().compareToIgnoreCase(p2.getNombre());
            }
        });
        var listas = listasService.listas();
        model.addAttribute("listas", listas);
        log.info("Ejecutando el controlador de Productos");
        model.addAttribute("produtos", productos);
        model.addAttribute("totalProductos", productos.size());
        return "productosInicio";
    }

    @GetMapping("/agregarProducto")
    public String agregar(Producto producto) {
        return "modificarProducto";
    }

    @PostMapping("/guardarProducto")
    public String guardar(@ModelAttribute Producto producto, Errors errores) {
        if (errores.hasErrors()) {
            return "modificarProducto";
        }
        productoService.guardar(producto);
        return "redirect:/productosInicio";
    }

    @GetMapping("/editarProducto/{idProducto}")
    public String editar(Producto producto, Model model) {
        producto = productoService.encontrarProducto(producto);
        model.addAttribute("producto", producto);
        return "modificarProducto";
    }

    @GetMapping("/eliminarProducto")
    public String eliminar(Producto producto) {
        productoService.eliminar(producto);
        return "redirect:/productosInicio";
    }

    @GetMapping("/cargasInicio")
    public String cargasInicio(Model model) {
        List<Producto> productosCarga = productoService.listarProductos();
        model.addAttribute("productosCarga", productosCarga);
        model.addAttribute("totalProductosCarga", productosCarga.size());
        return "cargasInicio";
    }

    /*
    *Los siguientes métodos fueron creados para modificar y guardar la existencia de los productos
    *o stock, ya que el cliente realiza dos tipos de ventas.
    *1). Venta directa: aquí se controla la carga y descarga de helados del camión.
    *2). Pre-venta: Para este caso no es necesario tener control del stock, es por eso que 
    *se utilizó el tipo FLOAT ya que para evitar los mensajes de producto agotado, pueda agregar
    *un número grande como stock.
    */
    @PostMapping("/guardarCargaProducto")
    public String guardarExistencia(@ModelAttribute Producto producto, RedirectAttributes redirectAttr, Errors errores) {
        if (errores.hasErrors()) {
            redirectAttr.addFlashAttribute("mensajeError", "Revise la cantidad ingresada");
        }
        productoService.guardar(producto);
        return "redirect:/cargasInicio";
    }

    @GetMapping("/editarExistencia/{idProducto}")
    public String editarExistencia(@ModelAttribute Producto producto, Model model) {
        Producto productoEdit = productoService.encontrarProducto(producto);
        model.addAttribute("productoEdit", productoEdit);
        return "modificarCarga";
    }
}
