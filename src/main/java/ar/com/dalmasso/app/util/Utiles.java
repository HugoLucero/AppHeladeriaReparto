/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
public class Utiles {
    public static String obtenerFecha(){
        String formato = "dd-MM-yyyy";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
        LocalDateTime fecha = LocalDateTime.now();
        return formateador.format(fecha);
    }
    
    public static String obtenerHora(){
        String formato = "HH:mm:ss";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
        LocalDateTime hora = LocalDateTime.now();
        return formateador.format(hora);
    }
    
    public static String obtenerFechaHora(){
        String formato = "dd-MM-yyyy || HH:mm";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
        LocalDateTime ahora = LocalDateTime.now();
        return formateador.format(ahora);
    }
}
