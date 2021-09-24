/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.dao;

import ar.com.dalmasso.app.domain.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author hugoa
 */
public interface OrdenDao extends JpaRepository<Orden, Long> {

    Orden findByFecha(String fecha);

    Orden findByPagado(boolean pagado);
}
