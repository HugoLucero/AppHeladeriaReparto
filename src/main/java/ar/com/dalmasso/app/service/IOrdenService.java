/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.domain.Orden;

import java.util.List;

import org.springframework.data.domain.Page;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
public interface IOrdenService {

    public List<Orden> listarOrdenes();

    public void guardar(Orden orden);

    public void eliminar(Orden orden);

    public Orden encontrarOrden(Orden orden);
    
    public Orden encontrarOrdenFecha(Orden orden);

    public Orden encontrarOrdenPagado(Orden orden);

    Page<Orden> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
