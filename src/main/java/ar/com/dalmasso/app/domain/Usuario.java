/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hugoa
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @OneToMany
    @JoinColumn(name="id_usuario")
    private List<Rol> roles;

    @Email(message = "El mail ingresado es incorrecto")
    @Column(length = 100, unique = true)
    private String mail;

    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(length = 500, unique = true)
    private String token;

}
