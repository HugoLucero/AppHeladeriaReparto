/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Hugo Lucero - Desarrollador Full - Stack
 */
public class EncriptPass {
    //Sólo para la encriptacion del password de manera local
    public static void main(String[] args) {
        var password = "admin";
        System.out.println("password: " + password);
        System.out.println("password encriptado: " + encriptarPassword(password));
        
        }
    public static String encriptarPassword( String password ){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.encode(password);
    }
}
