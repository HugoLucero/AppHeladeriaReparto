/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 *
 * @author hugoa
 */
@Data
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    @NotEmpty
    private String nombreCliente;
    @NotEmpty
    private String apellidoCliente;
    @NotEmpty
    private String direccionCliente;

    private String telefonoCliente;
    
    private Float saldoCliente;
    @NotEmpty
    private String zona;

    private boolean freezer;
    
    public Cliente(){
        
    }

    public Cliente(Long idCliente, String nombreCliente, String apellidoCliente, String direccionCliente, String telefonoCliente, String zona) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.direccionCliente = direccionCliente;
        this.telefonoCliente = telefonoCliente;
        this.zona = zona;
    }

    public Cliente(String nombreCliente, String apellidoCliente, String direccionCliente, String telefonoCliente, String zona) {
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.direccionCliente = direccionCliente;
        this.telefonoCliente = telefonoCliente;
        this.zona = zona;
    }
    
    public void aumentarSaldo(Float saldo){
        this.saldoCliente++;
    }
}
