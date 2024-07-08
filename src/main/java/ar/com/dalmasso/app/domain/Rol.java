/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.domain;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 *
 * @author hugoa
 */
@Entity
@Data
@Table(name="rol")
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idRol;
    
    @NotEmpty
    private String nombre;

}
