/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.dao;

import ar.com.dalmasso.app.domain.ProductosListas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
public interface ProductosListasDao extends JpaRepository<ProductosListas, Long> {
    @Query("select p from ProductosListas p where p.listasDePrecio.id = ?1 and p.producto.idProducto = ?2")
    ProductosListas findByListasDePrecio_IdAndProducto_IdProducto(Long id, Long idProducto);

}
