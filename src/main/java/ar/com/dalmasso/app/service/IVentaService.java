package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.domain.Cliente;
import ar.com.dalmasso.app.domain.Orden;
import ar.com.dalmasso.app.domain.Producto;
import ar.com.dalmasso.app.domain.ProductoParaVender;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public interface IVentaService {

    String quitarCliente(int indice, HttpServletRequest request);
    String quitarDelCarrito(int indice, HttpServletRequest request);
    String cancelarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs);
    String terminarVenta(Orden orden, Model model, boolean pagado,float modcant, String observaciones,
                                HttpServletRequest request, RedirectAttributes redirectAttrs);
    String interfazVender(Producto producto, ProductoParaVender ppv, Cliente cliente, Model model, HttpServletRequest request);
    String agregarAlCarrito(float cantidad, Producto producto, Long idLista, HttpServletRequest request, RedirectAttributes redirectAttrs, Model model);
    String agregarCliente(Cliente cliente, Model model, HttpServletRequest request, RedirectAttributes redirectAttrs);
    List<String> prodComAutocomplete(String term);
    public List<String> clienteAutoComplete(String term);
    public String modificar(Float precio, String nombre, HttpServletRequest request);
    public String modificarCantidad(Float modcant, String nom, HttpServletRequest request);


}
