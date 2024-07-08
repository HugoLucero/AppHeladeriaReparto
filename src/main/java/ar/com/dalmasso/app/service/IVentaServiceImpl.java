package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.dao.ClienteAgregadoDao;
import ar.com.dalmasso.app.dao.ProductoVendidoDao;
import ar.com.dalmasso.app.dao.ProductosListasDao;
import ar.com.dalmasso.app.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class IVentaServiceImpl implements IVentaService {

    private static final String REDIRECT_VENDER = "redirect:/vender/";
    private static final String MENSAJE = "mensaje";
    private static final String WARNING = "warning";
    private static final String CLASE = "clase";
    
    private final IProductoService productoService;
    private final IClienteService clienteService;
    private final IOrdenService ordenService;
    private final ProductoVendidoDao productoDao;
    private final ClienteAgregadoDao clienteDao;
    private final IListasService listasService;
    private final ProductosListasDao productosListasDao;

    @Autowired
    public IVentaServiceImpl(IProductoService productoService, IClienteService clienteService, IOrdenService ordenService,
                             ProductoVendidoDao productoDao, ClienteAgregadoDao clienteDao, IListasService listasService, ProductosListasDao productosListasDao) {
        this.productoService = productoService;
        this.clienteService = clienteService;
        this.ordenService = ordenService;
        this.productoDao = productoDao;
        this.clienteDao = clienteDao;
        this.listasService = listasService;
        this.productosListasDao = productosListasDao;
    }

    @Override
    public String quitarCliente(int indice, HttpServletRequest request) {
        ArrayList<ClienteParaAgregar> clientes = this.obtenerCliente(request);
        if (!clientes.isEmpty() && clientes.get(indice) != null) {
            clientes.remove(indice);
            this.guardarCliente(clientes, request);
        }
        return REDIRECT_VENDER;
    }

    @Override
    public String quitarDelCarrito(int indice, HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        if (!carrito.isEmpty() && carrito.get(indice) != null) {
            carrito.remove(indice);
            this.guardarCarrito(carrito, request);
        }
        return REDIRECT_VENDER;
    }

    @Override
    public String cancelarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        this.limpiarCarrito(request);
        redirectAttrs
                .addFlashAttribute(MENSAJE, "Venta cancelada")
                .addFlashAttribute(CLASE, "info");
        return REDIRECT_VENDER;
    }

    @Override
    public String terminarVenta(Orden orden, Model model, boolean pagado, float modcant, String observaciones, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        List<ClienteParaAgregar> clientes = this.obtenerCliente(request);
// Si no hay carrito o está vacío, regresamos inmediatamente
        if (clientes.isEmpty()) {
            return REDIRECT_VENDER;
        }

        List<ProductoParaVender> carrito = this.obtenerCarrito(request);
// Si no hay carrito o está vacío, regresamos inmediatamente
        if (carrito.isEmpty()) {
            return REDIRECT_VENDER;
        }

        if (!observaciones.isBlank()) {
            orden.setObservaciones(observaciones.trim());
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

// Indicamos una venta exitosa
        redirectAttrs
                .addFlashAttribute(MENSAJE, "Venta realizada correctamente")
                .addFlashAttribute(CLASE, "success");

        return REDIRECT_VENDER;

    }

    @Override
    public String interfazVender(Producto producto, ProductoParaVender ppv, Cliente cliente, Model model, HttpServletRequest request) {
        //Creamos la variable que itera los clientes y la ordenamos
        List<Cliente> clientes = clienteService.listarClientes();
        clientes.sort((c1, c2) -> c1.getNombreCliente().compareToIgnoreCase(c2.getNombreCliente()));
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

    @Override
    public String agregarAlCarrito(float cantidad, Producto producto, Long idLista, HttpServletRequest request, RedirectAttributes redirectAttrs, Model model) {
        //Obtenemos los productos de la session
        List<ProductoParaVender> carrito = this.obtenerCarrito(request);
        //Buscamos el producto que recibe de la vista
        Producto productoBuscadoPorNombre = productoService.encontrarProductoNombre(producto);

        if (idLista == null || idLista <= 0) {
            redirectAttrs
                    .addFlashAttribute(MENSAJE, "Por favor seleccione una lista. ")
                    .addFlashAttribute(CLASE, WARNING);
            return REDIRECT_VENDER;
        }
        ProductosListas productosListas = productosListasDao.findByListasDePrecio_IdAndProducto_IdProducto(idLista, productoBuscadoPorNombre.getIdProducto());
        if (productosListas == null || productosListas.getPrecio() == null || productosListas.getPrecio() == 0) {
            redirectAttrs
                    .addFlashAttribute(MENSAJE, "El producto con el nombre " + producto.getNombre() + " no tiene un precio informado")
                    .addFlashAttribute(CLASE, WARNING);
            return REDIRECT_VENDER;
        }
        Float precios = productosListas.getPrecio();
        //Validaciones correspondientes para el producto
        if (productoBuscadoPorNombre == null) {
            redirectAttrs
                    .addFlashAttribute(MENSAJE, "El producto con el nombre " + producto.getNombre() + " no existe")
                    .addFlashAttribute(CLASE, WARNING);
            return REDIRECT_VENDER;
        }
        if (productoBuscadoPorNombre.sinExistencia()) {
            redirectAttrs
                    .addFlashAttribute(MENSAJE, "El producto está agotado")
                    .addFlashAttribute(CLASE, WARNING);
            return REDIRECT_VENDER;
        }  //Aumentamos la cantidad si se encuentra el producto

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
        return REDIRECT_VENDER;
    }

    @Override
    public String agregarCliente(Cliente cliente, Model model, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        ArrayList<ClienteParaAgregar> clientes = this.obtenerCliente(request);
        Cliente clienteBuscadoPorNombre = clienteService.encontrarClienteNombre(cliente);
        if (clienteBuscadoPorNombre == null) {
            redirectAttrs
                    .addFlashAttribute(MENSAJE, "Seleccione un cliente de la Lista o no existe el cliente buscado")
                    .addFlashAttribute(CLASE, WARNING);
            return REDIRECT_VENDER;
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
        return REDIRECT_VENDER;
    }

    @Override
    public List<String> prodComAutocomplete(String term) {
        return productoService.getNombre(term);
    }

    @Override
    public List<String> clienteAutoComplete(String term) {
        return clienteService.getNombre(term);
    }

    @Override
    public String modificar(Float precio, String nombre, HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        for (ProductoParaVender productoParaVender : carrito) {
            if (productoParaVender.getNombre().equals(nombre)) {
                productoParaVender.setPrecio(precio);
            }
        }
        return REDIRECT_VENDER;
    }

    @Override
    public String modificarCantidad(Float modcant, String nom, HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = this.obtenerCarrito(request);
        for (ProductoParaVender productoParaVender : carrito) {
            if (productoParaVender.getNombre().equals(nom)) {
                productoParaVender.setCantidad(modcant);
            }
        }
        return REDIRECT_VENDER;
    }

    private void limpiarCarrito(HttpServletRequest request) {
        this.guardarCarrito(new ArrayList<>(), request);
        this.guardarCliente(new ArrayList<>(), request);
    }

    private ArrayList<ProductoParaVender> obtenerCarrito(HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = (ArrayList<ProductoParaVender>) request.getSession().getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        return carrito;
    }

    private void guardarCarrito(List<ProductoParaVender> carrito, HttpServletRequest request) {
        request.getSession().setAttribute("carrito", carrito);
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
}
