/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.dao.CargaDao;
import ar.com.dalmasso.app.domain.Carga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Service
public class CargaServiceImpl implements ICargaService{

    @Autowired
    private CargaDao cargaDao;
    
    @Override
    @Transactional(readOnly=true)
    public List<Carga> cargas() {
        return (List<Carga>)cargaDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Carga carga) {
        cargaDao.save(carga);
    }

    @Override
    @Transactional
    public void eliminar(Carga carga) {
        cargaDao.delete(carga);
    }

    @Override
    @Transactional
    public Carga encontrarCargaFecha(Carga carga) {
        return cargaDao.findByFecha(carga.getFecha());
    }

    @Override
    @Transactional(readOnly=true)
    public Carga encontrarCargaInicial(Carga carga) {
        return cargaDao.findByCargaInicial(carga.isCargaInicial());
    }

    @Override
    public Carga encontrarCarga(Carga carga) {
        return cargaDao.findById(carga.getIdCarga()).orElse(null);
    }

}
