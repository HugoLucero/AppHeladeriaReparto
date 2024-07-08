/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.domain;

import ar.com.dalmasso.app.util.Utiles;
import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
@Data
@Entity
@Table(name = "reporte")
public class Reporte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReporte;
    @Column(name = "fecha_y_hora")
    private String fechaYHora;

    private String observaciones;

    @OneToMany(mappedBy = "reporte")
    private List<Orden> ordenes;

    @OneToMany(mappedBy = "reporte")
    private List<ProductoVendido> productos;

    public Reporte() {
        this.fechaYHora = Utiles.obtenerFechaHora();
    }

    public List<Entry<String, Double>> getCa() {
        return productos.stream()
                .collect(Collectors.groupingBy(ProductoVendido::getNombre,
                        Collectors.summingDouble(ProductoVendido::getCantidad)))
                 .entrySet().stream()
                 .collect(Collectors.toList());
    }

    public Double getTotal(){
        return ordenes.stream().mapToDouble(Orden::getTotal).sum();
    }

}
