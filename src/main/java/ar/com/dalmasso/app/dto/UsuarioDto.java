package ar.com.dalmasso.app.dto;

import ar.com.dalmasso.app.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link Usuario}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto implements Serializable {

    private static final long serialVersionUID = 2405172041950251807L;


    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @Email(message = "El mail ingresado es incorrecto")
    private String mail;
    String fechaNacimiento;
    private String token;

    private List<String> roles = new ArrayList<>();
}