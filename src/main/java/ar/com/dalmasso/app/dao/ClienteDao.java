/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.dao;

import ar.com.dalmasso.app.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author hugoa
 */
public interface ClienteDao extends JpaRepository<Cliente, Long> {

    Cliente findByNombreCliente(String nombreCliente);

    Cliente findByApellidoCliente(String apellidoCliente);

    Cliente findByZona(String zona);

    Cliente findByFreezer(boolean freezer);
}
