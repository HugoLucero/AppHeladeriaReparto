/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.domain;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
public class ProductoParaVender extends Producto {
    
    private Float cantidad;
    
    public ProductoParaVender(Long idProducto, String codigo, String nombre, String tipo, String marca, Float precio, Float existencia, Float cantidad){
        super(idProducto, codigo, nombre, tipo, marca, precio, existencia);
        this.cantidad = cantidad;
    }
    
    public ProductoParaVender(String codigo, String nombre, String tipo, String marca, Float precio, Float cantidad){
        super(codigo, nombre, tipo, marca, precio, cantidad);
        this.cantidad = cantidad;
    }

    public ProductoParaVender() {
    }
    
    public void aumentarCantidad(){
        this.cantidad++;
    }
    
    public float getCantidad(){
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }
    
    public float getTotal(){
        return this.getPrecio() * this.cantidad;
    }
}
