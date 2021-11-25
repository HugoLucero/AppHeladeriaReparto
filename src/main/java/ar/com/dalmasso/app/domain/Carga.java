/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.domain;

import ar.com.dalmasso.app.util.Utiles;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author hugoa
 */
@Data
@Entity
@Table(name="carga")
public class Carga implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarga;
    private String fecha;
    @Column(name="fecha_y_hora")
    private String fechaYHora;
    
    private String observaciones;
    @NotNull
    private boolean cargaInicial;
    @NotEmpty
    @OneToMany
    @JoinColumn(name="control_carga")
    private List<Usuario> usuarios;
    
    @NotEmpty
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="productos")
    private List<Producto> productos;

    
    public Carga(){
        this.fechaYHora = Utiles.obtenerFechaHora();
    }
    
    public Carga(String fecha){
        this.fecha = Utiles.obtenerFecha();
    }
}
