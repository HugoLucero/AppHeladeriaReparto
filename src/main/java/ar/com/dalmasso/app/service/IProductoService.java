/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.domain.Producto;

import java.util.List;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
public interface IProductoService {

    public List<Producto> listarProductos();

    public void guardar(Producto producto);

    public void eliminar(Producto producto);

    public Producto encontrarProducto(Producto producto);
    
    public Producto encontrarProductoNombre(Producto producto);

    public Producto encontrarProductoCodigo(Producto producto);

    public Producto encontrarProductorMarca(Producto producto);

    public Producto encontrarProductoTipo(Producto producto);

    public Producto encontrarProductoPrecio(Producto producto);

    public List<String> getNombre(String term);
}
