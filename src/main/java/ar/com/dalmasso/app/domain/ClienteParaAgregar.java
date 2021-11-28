/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.domain;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
public class ClienteParaAgregar extends Cliente {

    public ClienteParaAgregar(Long idCliente, String nombreCliente, String apellidoCliente, String direccionCliente, String telefonoCliente, String zona) {
        super(idCliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, zona);
    }

    public ClienteParaAgregar(String nombreCliente, String apellidoCliente, String direccionCliente, String telefonoCliente, String zona) {
        super(nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, zona);
    }
    
}
