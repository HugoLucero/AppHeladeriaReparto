/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.dao.OrdenDao;
import ar.com.dalmasso.app.domain.Orden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Service
public class OrdenServiceImpl implements IOrdenService{

    @Autowired
    private OrdenDao ordenDao;
    
    @Override
    @Transactional(readOnly=true)
    public List<Orden> listarOrdenes() {
        return (List<Orden>)ordenDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Orden orden) {
        ordenDao.save(orden);
    }

    @Override
    @Transactional
    public void eliminar(Orden orden) {
        ordenDao.delete(orden);
    }

    @Override
    @Transactional(readOnly=true)
    public Orden encontrarOrdenFecha(Orden orden) {
        return ordenDao.findByFecha(orden.getFecha());
    }

    @Override
    @Transactional(readOnly=true)
    public Orden encontrarOrdenPagado(Orden orden) {
        return ordenDao.findByPagado(orden.isPagado());
    }

    @Override
    @Transactional(readOnly=true)
    public Orden encontrarOrden(Orden orden) {
        return ordenDao.findById(orden.getIdOrden()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Orden> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.ordenDao.findAll(pageable);
    }

}
