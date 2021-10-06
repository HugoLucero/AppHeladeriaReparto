/*
 * Software creado y diseñado por Hugo Lucero.
 * Derechos reservados para Hugo Lucero.
 * Para más información contactarse a:
 * hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.web;

import ar.com.dalmasso.app.dao.ListasDePrecioDao;
import ar.com.dalmasso.app.dao.ProductoDao;
import ar.com.dalmasso.app.dao.ProductosListasDao;
import ar.com.dalmasso.app.domain.ListasDePrecio;
import ar.com.dalmasso.app.domain.Producto;
import ar.com.dalmasso.app.domain.ProductosListas;
import ar.com.dalmasso.app.service.IListasService;
import ar.com.dalmasso.app.service.IProductoListaService;
import ar.com.dalmasso.app.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Controller
public class ControllerListas {

    @Autowired
    private IListasService listasService;

    @Autowired
    private IProductoListaService productosDao;

    @Autowired
    private ProductosListasDao pDao;

    @Autowired
    private IProductoService productoService;

    @Autowired
    private ProductoDao prodDao;

    @Autowired
    private ListasDePrecioDao listaDao;


    //Creamos la variable y los métodos para manipular las listas y los productos de las listas
    @GetMapping(value = "/listasInicio")
    public String inicio(ProductosListas producto, Model model) {
        var listas = listasService.listas();
        model.addAttribute("productosListas", producto);
        model.addAttribute("listas", listas);
        return "listasInicio";
    }


    @PostMapping(value = "/guardarLista")
    public String guardar(ListasDePrecio lista, Model model, Errors errores) {
        //Obtenemos los productos que se agregaran a la lista de precio
        var productos = productoService.listarProductos();
        model.addAttribute("produc", productos);
        if (errores.hasErrors()) {
            return "listasInicio";
        }
        //Primero creamos la lista para poder obtener el id y que se agregue a la llave foránea.
        //Luego agregamos los productos pero con un precio nuevo
        listasService.guardar(lista);
        for (Producto prod : productos) {
            Producto p = productoService.encontrarProducto(prod);
            if (prod.getIdProducto().equals(p.getIdProducto())) {
                ProductosListas nuevo = new ProductosListas(0f, prod, lista);
                productosDao.guardar(nuevo);
            }

        }
        return "redirect:/listasInicio";
    }

    @GetMapping(value = "/eliminarLista")
    public String eliminar(ListasDePrecio lista) {
        listasService.eliminar(lista);
        return "redirect:/listasInicio";
    }

    @GetMapping(value = "/verLista/{id}")
    public String verLista(ListasDePrecio lista, Model model) {
        lista = listasService.encontrarLista(lista);
        model.addAttribute("lista", lista);
        var productos = productoService.listarProductos();
        model.addAttribute("product", productos);

        return "visualizarLista";
    }

    @PostMapping(value = "/agregarProducto")
    public String agregarProducto(@RequestParam(value = "listaEncontrar", defaultValue = "") Long listasDePrecio, RedirectAttributes rdr
            , @RequestParam(value = "producto", defaultValue = "") String nombreProducto) {
        var lista = listaDao.getById(listasDePrecio);
        var producto = prodDao.findByNombre(nombreProducto);
        var productosLista = lista.getProductos();
        if (producto == null) {
            rdr.addFlashAttribute("mensaje", "Seleccione un Producto de la lista.");
        }
        ProductosListas nuevoProducto = new ProductosListas(0f, producto, lista);
        productosLista.add(nuevoProducto);
        listasService.guardar(lista);
        return "redirect:/verLista/" + listasDePrecio;
    }

    /* --------------------------------------------------------------------------------------------------------- */
    //Utilizamos este método para poder modificar los precios 
    //de la lista creada anteriormente, teniendo la posibilidad de modificar 
    //cada uno de los productos.
    @PostMapping(value = "/editarProd")
    public String editarProducto(@RequestParam(value = "precio", defaultValue = "0") Float precio,
                                 @RequestParam(value = "id", defaultValue = "") Long id, @RequestParam(value = "idLista", defaultValue = "") Long idLista) {
        ProductosListas producto = pDao.getById(id);
        producto.setPrecio(precio);
        pDao.save(producto);
        return "redirect:/verLista/" + idLista;
    }
}
