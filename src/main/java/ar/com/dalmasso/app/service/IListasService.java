/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.domain.ListasDePrecio;
import java.util.List;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
public interface IListasService {
    
    public List<ListasDePrecio> listas();
    
    public ListasDePrecio encontrarLista(ListasDePrecio lista);
    
    public ListasDePrecio buscarListaPorNombre (ListasDePrecio lista);
    
    public void guardar (ListasDePrecio lista);
    
    public void eliminar (ListasDePrecio lista);
}
