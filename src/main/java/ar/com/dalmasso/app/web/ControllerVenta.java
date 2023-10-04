/*
 * Software creado y diseñado por Hugo Lucero.
 * Derechos reservados para Hugo Lucero.
 * Para más información contactarse a:
 * hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.web;

import ar.com.dalmasso.app.dao.ClienteAgregadoDao;
import ar.com.dalmasso.app.dao.ListasDePrecioDao;
import ar.com.dalmasso.app.dao.ProductoVendidoDao;
import ar.com.dalmasso.app.dao.ProductosListasDao;
import ar.com.dalmasso.app.domain.*;
import ar.com.dalmasso.app.service.IClienteService;
import ar.com.dalmasso.app.service.IListasService;
import ar.com.dalmasso.app.service.IOrdenService;
import ar.com.dalmasso.app.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Controller
@RequestMapping(path = "/vender")
public class ControllerVenta {

    @Autowired
    private IProductoService productoService;
    @Autowired
    private IClienteService clienteService;
    @Autowired
    private IOrdenService ordenService;
    @Autowired
    private ProductoVendidoDao productoDao;
    @Autowired
    private ClienteAgregadoDao clienteDao;
    @Autowired
    private IListasService listasService;
    @Autowired
    private ListasDePrecioDao listaDao;
    @Autowired
    private ProductosListasDao productosListasDao;


    @PostMapping(value = "/quitarCliente/{indice}")
    public String quitarCliente(@PathVariable int indice, HttpServletRequest request) {
        ArrayList<ClienteParaAgregar> clientes = this.obtenerCliente(request);
        if (clientes != null && clientes.size() > 0 && clientes.get(indice) != null) {
            clientes.remove(indice);
            this.guardarCliente(clientes, request);
        }
        return "redirect:/vender/";
    }

    @PostMapping(value = "/quitar/{indice}")
    public String quitarDelCarrito(@PathVariable int indice, HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        if (carrito != null && carrito.size() > 0 && carrito.get(indice) != null) {
            carrito.remove(indice);
            this.guardarCarrito(carrito, request);
        }
        return "redirect:/vender/";
    }

    private void limpiarCarrito(HttpServletRequest request) {
        this.guardarCarrito(new ArrayList<>(), request);
        this.guardarCliente(new ArrayList<>(), request);
    }

    @GetMapping(value = "/limpiar")
    public String cancelarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        this.limpiarCarrito(request);
        redirectAttrs
                .addFlashAttribute("mensaje", "Venta cancelada")
                .addFlashAttribute("clase", "info");
        return "redirect:/vender/";
    }

    @PostMapping(value = "/terminar")
    public String terminarVenta(Orden orden, Model model, @RequestParam(value = "pagado", defaultValue = "false") boolean pagado,
                                @RequestParam(value = "modCant", defaultValue = "0") float modcant, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        ArrayList<ClienteParaAgregar> clientes = this.obtenerCliente(request);
        // Si no hay carrito o está vací­o, regresamos inmediatamente
        if (clientes == null || clientes.size() <= 0) {
            return "redirect:/vender/";
        }

        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        // Si no hay carrito o está vací­o, regresamos inmediatamente
        if (carrito == null || carrito.size() <= 0) {
            return "redirect:/vender/";
        }

        ordenService.guardar(orden);
        // Recorrer los clientes
        for (ClienteParaAgregar clienteParaAgregar : clientes) {
            // Obtener el cliente fresco desde la base de datos
            Cliente c = clienteService.encontrarCliente(clienteParaAgregar);
            if (c == null) {
                continue; // Si es nulo o no existe, ignoramos el siguiente cliente con continue
            }
            // Creamos un nuevo cliente que será el que se guarda junto con la venta
            ClienteAgregado clienteAgregado = new ClienteAgregado(clienteParaAgregar.getNombreCliente(), clienteParaAgregar.getApellidoCliente(), clienteParaAgregar.getDireccionCliente(), clienteParaAgregar.getZona(), orden);
            // Y lo guardamos
            clienteDao.save(clienteAgregado);
        }

        // Recorrer el carrito
        for (ProductoParaVender productoParaVender : carrito) {
            // Obtener el producto fresco desde la base de datos
            Producto p = productoService.encontrarProducto(productoParaVender);
            if (p == null) {
                continue; // Si es nulo o no existe, ignoramos el siguiente código con continue
            }            // Le restamos existencia
            p.restarExistencia(productoParaVender.getCantidad());
            // Lo guardamos con la existencia ya restada
            productoService.guardar(p);
            // Creamos un nuevo producto que será el que se guarda junto con la venta
            ProductoVendido productoVendido = new ProductoVendido(productoParaVender.getCantidad(), productoParaVender.getPrecio(), productoParaVender.getNombre(), productoParaVender.getCodigo(), orden);
            // Y lo guardamos
            productoDao.save(productoVendido);
        }

        // Al final limpiamos el carrito
        this.limpiarCarrito(request);
        // e indicamos una venta exitosa
        redirectAttrs
                .addFlashAttribute("mensaje", "Venta realizada correctamente")
                .addFlashAttribute("clase", "success");
        return "redirect:/vender/";
    }

    @GetMapping(value = "/")
    public String interfazVender(Producto producto, ProductoParaVender ppv, Cliente cliente, Model model, HttpServletRequest request) {
        //Creamos la variable que itera los clientes y la ordenamos 
        List<Cliente> clientes = clienteService.listarClientes();
        Collections.sort(clientes, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c1, Cliente c2) {
                return c1.getNombreCliente().compareToIgnoreCase(c2.getNombreCliente());
            }
        });
        model.addAttribute("listarClientes", clientes);
        model.addAttribute("producto", producto);
        model.addAttribute("cliente", cliente);
        model.addAttribute("ppv", ppv);

        var listas = listasService.listas();
        model.addAttribute("listas", listas);
        //Variable que muestra el total del carrito
        float total = 0;
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        for (ProductoParaVender p : carrito) {
            total += p.getTotal();
        }

        model.addAttribute("total", total);
        model.addAttribute("totalProductos", carrito.size());

        return "vender/vender";
    }

    private ArrayList<ProductoParaVender> obtenerCarrito(HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = (ArrayList<ProductoParaVender>) request.getSession().getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        return carrito;
    }

    private void guardarCarrito(ArrayList<ProductoParaVender> carrito, HttpServletRequest request) {
        request.getSession().setAttribute("carrito", carrito);
    }

    //Creacion del carrito
    @PostMapping(value = "/agregarAlCarro")
    public String agregarAlCarrito(@RequestParam(value = "cantidad", defaultValue = "1") float cantidad, @ModelAttribute Producto producto,
                                   @RequestParam(value = "idLista", defaultValue = "") Long idLista, HttpServletRequest request, RedirectAttributes redirectAttrs, Model model) {
        //Obtenemos los productos de la session
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        //Buscamos el producto que recibe de la vista
        Producto productoBuscadoPorNombre = productoService.encontrarProductoNombre(producto);

        if (idLista == null || idLista <= 0) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Por favor seleccione una lista. ")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/vender/";
        }
        Float precios = null;
        ProductosListas productosListas = productosListasDao.findByListasDePrecio_IdAndProducto_IdProducto(idLista, productoBuscadoPorNombre.getIdProducto());
        if (productosListas == null || productosListas.getPrecio() == null || productosListas.getPrecio() == 0) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "El producto con el nombre " + producto.getNombre() + " no tiene un precio informado")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/vender/";
        }
        precios = productosListas.getPrecio();
        //Validaciones correspondientes para el producto
        if (productoBuscadoPorNombre == null) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "El producto con el nombre " + producto.getNombre() + " no existe")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/vender/";
        }
        if (productoBuscadoPorNombre.sinExistencia()) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "El producto está agotado")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/vender/";
        } else {
        }//Aumentamos la cantidad si se encuentra el producto
        boolean encontrado = false;
        for (ProductoParaVender productoParaVenderActual : carrito) {
            if (productoParaVenderActual.getNombre().equals(productoBuscadoPorNombre.getNombre())) {
                productoParaVenderActual.aumentarCantidad();
                encontrado = true;
                break;
            }
        }
        //El argumento correspondiente a cantidad se obtiene como parámetro de la vista
        //y colocamos por default la cantidad de 1. Y el precio lo obtenemos de la varibale local
        //creada para la lista.
        if (!encontrado) {
            carrito.add(new ProductoParaVender(productoBuscadoPorNombre.getIdProducto(), productoBuscadoPorNombre.getCodigo(), productoBuscadoPorNombre.getNombre(), productoBuscadoPorNombre.getTipo(), productoBuscadoPorNombre.getMarca(),
                    precios, productoBuscadoPorNombre.getExistencia(), cantidad));
        }
        this.guardarCarrito(carrito, request);
        return "redirect:/vender/";
    }

    @PostMapping(value = "/agregarCliente")
    public String agregarCliente(@ModelAttribute Cliente cliente, Model model, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        ArrayList<ClienteParaAgregar> clientes = this.obtenerCliente(request);
        Cliente clienteBuscadoPorNombre = clienteService.encontrarClienteNombre(cliente);
        if (clienteBuscadoPorNombre == null) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Seleccione un cliente de la Lista o no existe el cliente buscado")
                    .addFlashAttribute("clase", "warning");
            return "redirect:/vender/";
        }
        boolean encontrado = false;
        for (ClienteParaAgregar clienteParaAgregar : clientes) {
            if (clienteParaAgregar.getNombreCliente().equals(clienteBuscadoPorNombre.getNombreCliente())) {
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            clientes.add(new ClienteParaAgregar(clienteBuscadoPorNombre.getIdCliente(), clienteBuscadoPorNombre.getNombreCliente(), clienteBuscadoPorNombre.getApellidoCliente(), clienteBuscadoPorNombre.getDireccionCliente(), clienteBuscadoPorNombre.getTelefonoCliente(), clienteBuscadoPorNombre.getZona()));
        }
        this.guardarCliente(clientes, request);
        return "redirect:/vender/";
    }

    private ArrayList<ClienteParaAgregar> obtenerCliente(HttpServletRequest rqs) {
        ArrayList<ClienteParaAgregar> clientes = (ArrayList<ClienteParaAgregar>) rqs.getSession().getAttribute("clientes");
        if (clientes == null) {
            clientes = new ArrayList<>();
        }
        return clientes;
    }

    private void guardarCliente(ArrayList<ClienteParaAgregar> clientes, HttpServletRequest request) {
        request.getSession().setAttribute("clientes", clientes);
    }

    //Método para poder generar el autocomplete
    @GetMapping(value = "/prodComAutocomplete")
    @ResponseBody
    public List<String> prodComAutocomplete(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        return productoService.getNombre(term);
    }

    @GetMapping(value = "/clienteAutoComplete")
    @ResponseBody
    public List<String> clienteAutoComplete(@RequestParam(value = "term", required = false, defaultValue="")String term) {
        return clienteService.getNombre(term);
    }


    //------------------------------------------------------------------------------------------------------------------
    //Métodos que generan la posibilidad modificar el precio y la cantidad del producto
    //que se encuentra en el carrito sin modificar el objeto de la base de datos.

    @PostMapping(value = "/modificar")
    public String modificar(@RequestParam(value = "precio", required = false) Float precio, @RequestParam(value = "nombre", required = false, defaultValue = "") String nombre,
                            HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        for (ProductoParaVender productoParaVender : carrito) {
            if (productoParaVender.getNombre().equals(nombre)) {
                productoParaVender.setPrecio(precio);
            }
        }
        return "redirect:/vender/";
    }

    @PostMapping(value = "/modificarCant")
    public String modificarCantidad(@RequestParam(value = "modcant", defaultValue = "") Float modcant, @RequestParam(value = "nom", required = false, defaultValue = "") String nom,
                                    HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        for (ProductoParaVender productoParaVender : carrito) {
            if (productoParaVender.getNombre().equals(nom)) {
                productoParaVender.setCantidad(modcant);
            }
        }
        return "redirect:/vender/";
    }
}
