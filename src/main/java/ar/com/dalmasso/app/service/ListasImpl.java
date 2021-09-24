/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.dao.ListasDePrecioDao;
import ar.com.dalmasso.app.domain.ListasDePrecio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Service
public class ListasImpl implements IListasService {

    @Autowired
    private ListasDePrecioDao listasDao;
    
    @Override
    @Transactional(readOnly=true)
    public List<ListasDePrecio> listas() {
        return (List<ListasDePrecio>)listasDao.findAll();
    }

    @Override
    @Transactional(readOnly=true)
    public ListasDePrecio buscarListaPorNombre(ListasDePrecio lista) {
        return listasDao.findByNombre(lista.getNombre());
    }

    @Override
    @Transactional
    public void guardar(ListasDePrecio lista) {
        listasDao.save(lista);
    }

    @Override
    @Transactional
    public void eliminar(ListasDePrecio lista) {
        listasDao.delete(lista);
    }

    @Override
    @Transactional(readOnly=true)
    public ListasDePrecio encontrarLista(ListasDePrecio lista) {
        return listasDao.findById(lista.getId()).orElse(null);
    }
    
}
