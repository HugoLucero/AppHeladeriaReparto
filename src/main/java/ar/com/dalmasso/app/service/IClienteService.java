/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.service;

import ar.com.dalmasso.app.domain.Cliente;
import ar.com.dalmasso.app.domain.Orden;

import java.util.List;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
public interface IClienteService {

    public List<Cliente> listarClientes();

    public List<Orden> listarOrdenes();

    public void guardar(Cliente cliente);

    public void eliminar(Cliente cliente);

    public Cliente encontrarCliente(Cliente cliente);

    public Cliente encontrarClienteNombre(Cliente cliente);

    public Cliente encontrarClienteZona(Cliente cliente);

    public Cliente encontrarClienteFreezer(Cliente cliente);
}
