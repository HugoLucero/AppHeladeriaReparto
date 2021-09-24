/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Data
@Entity
@Table(name = "productos_listas")
public class ProductosListas implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productoId;
    @NotNull
    private Float precio;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producto")
    private Producto producto;
    
    @ManyToOne
    @JoinColumn(name = "lista")
    private ListasDePrecio listasDePrecio;

    public ProductosListas() {
    }
    
    

    public ProductosListas(Float precio, Producto producto, ListasDePrecio listasDePrecio) {
        this.precio = precio;
        this.producto = producto;
        this.listasDePrecio = listasDePrecio;
    }
    
    
}
