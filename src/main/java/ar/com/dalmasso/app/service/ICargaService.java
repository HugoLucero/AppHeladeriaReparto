/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.domain.Carga;

import java.util.List;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
public interface ICargaService {
    public List<Carga> cargas();
    public void guardar(Carga carga);
    public void eliminar(Carga carga);
    public Carga encontrarCarga(Carga carga);
    public Carga encontrarCargaFecha(Carga carga);
    public Carga encontrarCargaInicial(Carga carga);
}
