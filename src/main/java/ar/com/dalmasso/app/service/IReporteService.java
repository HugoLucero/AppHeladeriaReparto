/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.domain.Reporte;

import java.util.List;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
public interface IReporteService {

    public List<Reporte> listarReportes();

    public void guardar(Reporte reporte);

    public void eliminar(Reporte reporte);

    public Reporte encontrarReporte(Reporte reporte);
}
