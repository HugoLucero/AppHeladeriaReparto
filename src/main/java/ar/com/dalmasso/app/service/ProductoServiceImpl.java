/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.dao.ProductoDao;
import ar.com.dalmasso.app.domain.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private ProductoDao productoDao;
    
    @Override
    @Transactional(readOnly=true)
    public List<Producto> listarProductos() {
        return (List<Producto>)productoDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Producto producto) {
        productoDao.save(producto);
    }

    @Override
    @Transactional
    public void eliminar(Producto producto) {
        productoDao.delete(producto);
    }

    @Override
    @Transactional(readOnly=true)
    public Producto encontrarProducto(Producto producto) {
        return productoDao.findById(producto.getIdProducto()).orElse(null);
    }

    @Override
    @Transactional(readOnly=true)
    public Producto encontrarProductorMarca(Producto producto) {
        return productoDao.findByMarca(producto.getMarca());
    }

    @Override
    @Transactional(readOnly=true)
    public Producto encontrarProductoTipo(Producto producto) {
        return productoDao.findByTipo(producto.getTipo());
    }

    @Override
    @Transactional(readOnly=true)
    public Producto encontrarProductoPrecio(Producto producto) {
        return productoDao.findByPrecio(producto.getPrecio());
    }

    @Override
    @Transactional(readOnly=true)
    public Producto encontrarProductoCodigo(Producto producto) {
        return productoDao.findByCodigo(producto.getCodigo());
    }

    @Override
    @Transactional(readOnly = true)
    public Producto encontrarProductoNombre(Producto producto) {
        return productoDao.findByNombre(producto.getNombre());
    }

    @Override
    @Transactional
    public List<String> getNombre(String term) {
        return productoDao.getNombre(term);
    }
    
}
