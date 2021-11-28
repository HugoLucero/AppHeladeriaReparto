/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.domain.ProductosListas;
import java.util.List;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
public interface IProductoListaService {
    
    public List<ProductosListas> listarProductos();
    
    public ProductosListas encontrarProducto(ProductosListas productosListas);
    
    public void guardar(ProductosListas productosListas);
    
    public void eliminar(ProductosListas productosListas);
}
