/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.dao;

import ar.com.dalmasso.app.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * @author hugoa
 */
public interface ClienteDao extends JpaRepository<Cliente, Long> {

    //Creamos un query personalizadp para realizar la busqueda del autocomplete
    @Modifying
    @Query(value = "select c.nombre_cliente from cliente c where c.nombre_cliente LIKE %:term%", nativeQuery = true)
    List<String> getNombre(String term);

    List<Cliente> findByNombreClienteLikeIgnoreCase(String term);

    Cliente findByNombreCliente(String nombreCliente);

    Cliente findByApellidoCliente(String apellidoCliente);

    Cliente findByZona(String zona);

    Cliente findByFreezer(boolean freezer);
}
