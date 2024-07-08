/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.domain;

import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Data
@Entity
@Table(name="cliente_agregado")
public class ClienteAgregado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String apellido;

    private String direccion;

    private String zona;
    @ManyToOne
    @JoinColumn(name = "id_orden")
    private Orden orden;
    
    public ClienteAgregado(){
        
    }

    public ClienteAgregado(String nombre, String apellido, String direccion, String zona, Orden orden) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.zona = zona;
        this.orden = orden;
    }

    public ClienteAgregado(Long id, String nombre, String apellido, String direccion, String zona, Orden orden) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.zona = zona;
        this.orden = orden;
    }

    
    
}
