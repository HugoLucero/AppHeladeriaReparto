/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.dao.ProductosListasDao;
import ar.com.dalmasso.app.domain.ProductosListas;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Service
public class PListasImpl implements IProductoListaService {
    
    @Autowired
    private ProductosListasDao productosDao;

    @Override
    @Transactional(readOnly = true)
    public List<ProductosListas> listarProductos() {
        return productosDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductosListas encontrarProducto(ProductosListas productosListas) {
        return productosDao.findById(productosListas.getProductoId()).orElse(null);
    }

    @Override
    @Transactional
    public void guardar(ProductosListas productosListas) {
        productosDao.save(productosListas);
    }

    @Override
    @Transactional
    public void eliminar(ProductosListas productosListas) {
        productosDao.delete(productosListas);
    }
    
}
