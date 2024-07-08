package ar.com.dalmasso.app.domain;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link Usuario}
 */
@Value
public class UsuarioDto implements Serializable {
    @NotEmpty
    String username;
    @NotEmpty
    String password;
    @Email(message = "El mail ingresado es incorrecto")
    String mail;
    Date fechaNacimiento;
    String token;
}