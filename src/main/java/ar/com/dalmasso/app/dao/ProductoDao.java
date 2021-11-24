/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.dao;

import ar.com.dalmasso.app.domain.Producto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author hugoa
 */
public interface ProductoDao extends JpaRepository<Producto, Long> {

    //Creamos un query personalizadp para realizar la busqueda del autocomplete
    @Modifying
    @Query(value = "select p.nombre from producto p where p.nombre LIKE %:term%", nativeQuery = true)
    List<String> getNombre(String term);

    Producto findByCodigo(String codigo);

    Producto findByNombre(String nombre);

    Producto findByMarca(String marca);

    Producto findByTipo(String tipo);

    Producto findByPrecio(double precio);
}
