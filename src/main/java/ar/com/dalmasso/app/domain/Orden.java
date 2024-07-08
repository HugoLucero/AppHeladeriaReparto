/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.domain;

import ar.com.dalmasso.app.util.Utiles;
import lombok.Data;
import lombok.ToString;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author hugoa
 */
@Data
@Entity
@Table(name = "orden")
public class Orden implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrden;
    @Column(name = "fecha_y_hora")
    private String fechaYHora;
    private String fecha;

    private boolean pagado;

    private String observaciones;

    @ToString.Exclude
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductoVendido> productos;
    @ToString.Exclude
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<ClienteAgregado> clientes;

    @ManyToOne
    @JoinColumn(name = "orden_reporte")
    private Reporte reporte;

    public Orden(Long idOrden) {
        this.idOrden = idOrden;
    }

    public Orden(Long idOrden, String fechaYHora, String fecha, boolean pagado, String observaciones, List<ProductoVendido> productos, List<ClienteAgregado> clientes, Reporte reporte) {
        this.idOrden = idOrden;
        this.fechaYHora = fechaYHora;
        this.fecha = fecha;
        this.pagado = pagado;
        this.observaciones = observaciones;
        this.productos = productos;
        this.clientes = clientes;
        this.reporte = reporte;
    }

    public Orden() {
        this.fechaYHora = Utiles.obtenerFechaHora();
    }

    public Orden(String fecha) {
        this.fecha = Utiles.obtenerFecha();
    }

    public String pagado() {
        if (pagado == false) {
            return "No";
        }
        return "Sí";
    }

    public Float getTotal() {
        Float total = 0f;
        for (ProductoVendido productoVendido : this.productos) {
            total += productoVendido.getTotal();
        }
        return total;
    }

    public Double getCant() {
        double cantidadTotal = productos.stream()
                .mapToDouble(o -> o.getCantidad())
                .sum();
        return cantidadTotal;
    }

}
