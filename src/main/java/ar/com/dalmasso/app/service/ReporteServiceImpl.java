/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.dao.ReporteDao;
import ar.com.dalmasso.app.domain.Reporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Service
public class ReporteServiceImpl implements IReporteService {

    @Autowired
    private ReporteDao reporteDao;
    
    @Override
    @Transactional(readOnly=true)
    public List<Reporte> listarReportes() {
        return reporteDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Reporte reporte) {
        reporteDao.save(reporte);
    }

    @Override
    @Transactional
    public void eliminar(Reporte reporte) {
        reporteDao.delete(reporte);
    }

    @Override
    @Transactional(readOnly=true)
    public Reporte encontrarReporte(Reporte reporte) {
        return reporteDao.findById(reporte.getIdReporte()).orElse(null);
    }
    
}
