/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.dao;

import ar.com.dalmasso.app.domain.Carga;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author hugoa
 */
public interface CargaDao extends JpaRepository<Carga, Long> {

    Carga findByFecha(String fecha);

    Carga findByCargaInicial(boolean cargaInicial);

}
