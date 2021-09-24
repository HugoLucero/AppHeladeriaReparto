/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.dao;

import ar.com.dalmasso.app.domain.ListasDePrecio;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
public interface ListasDePrecioDao extends JpaRepository<ListasDePrecio, Long>{
    
    ListasDePrecio findByNombre(String nombre);
    
}
