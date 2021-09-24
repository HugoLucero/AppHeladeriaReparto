/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Data
@Entity
@Table(name = "producto_vendido")
public class ProductoVendido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;
    private Float cantidad, precio;
    private String nombre, codigo;
    
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "orden_id")
    private Orden orden;
    @ManyToOne
    @JoinColumn(name = "reporte_id")
    private Reporte reporte;

    public Float getTotal() {
        return cantidad * this.precio;
    }

    public ProductoVendido() {

    }

    public ProductoVendido(Float cantidad, Float precio, String nombre, String codigo, Orden orden) {
        this.cantidad = cantidad;
        this.precio = precio;
        this.nombre = nombre;
        this.codigo = codigo;
        this.orden = orden;
    }

    public ProductoVendido(Long id, Float cantidad, Float precio, String nombre, String codigo, Orden orden, Reporte reporte) {
        this.id = id;
        this.cantidad = cantidad;
        this.precio = precio;
        this.nombre = nombre;
        this.codigo = codigo;
        this.orden = orden;
        this.reporte = reporte;
    }

    
    
}
