/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 *
 * @author Hugo Lucero - Desarrollador Full-Stack
 */
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @NotEmpty
    private String codigo;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String tipo;
    @NotEmpty
    private String marca;

    private Float precio;

    private Float existencia;
    
    public Producto(Long idProducto, String codigo, String nombre, String tipo, String marca, Float precio, Float existencia) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.marca = marca;
        this.precio = precio;
        this.existencia = existencia;
    }

    public Producto(String codigo, String nombre, String tipo, String marca, Float precio, Float existencia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.marca = marca;
        this.precio = precio;
        this.existencia = existencia;
    }

    public Producto() {

    }

    public Producto(String codigo) {
        this.codigo = codigo;
    }

    public boolean sinExistencia() {
        return this.existencia <= 0;
    }

    public void restarExistencia(float existencia) {
        this.existencia -= existencia;
    }

    public void aumentarExistencia() {
        this.existencia++;
    }

    public void aumentarExistencia(float cantidad) {
        this.existencia++;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Float getExistencia() {
        return existencia;
    }

    public void setExistencia(Float existencia) {
        this.existencia = existencia;
    }

    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", codigo=" + codigo + ", nombre=" + nombre + ", tipo=" + tipo + ", marca=" + marca + ", precio=" + precio + ", existencia=" + existencia + '}';
    }
    
    
}
