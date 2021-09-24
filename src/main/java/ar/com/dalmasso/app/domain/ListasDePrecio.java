/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Data
@Entity
@Table(name = "listas_precios")
public class ListasDePrecio implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String nombre;
    
    @OneToMany(mappedBy = "listasDePrecio",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductosListas> productos;
    
}
